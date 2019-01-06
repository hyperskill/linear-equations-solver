// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include "solver.hpp"

#include <cassert>
#include <cinttypes>
#include <climits>
#include <numeric>
#include <iostream>
#include <fstream>

#include "complex.hpp"

using std::cout;
using std::endl;
using std::iota;
using std::istream;
using std::string;
using std::size_t;
using std::complex;
using std::nullopt;
using std::uint32_t;
using std::ifstream;
using std::vector;
using std::optional;
using std::ofstream;
using std::ostream;
using std::swap;

constexpr complex<double> ZERO (0.0, 0.0);
constexpr complex<double> ONE (1.0, 0.0);
constexpr complex<double> MINUS_ONE (-1.0, 0.0);

solver::solver (istream& in, bool verbose /* = false */)
    : number_solutions_ (number_solutions::one)
    , verbose (verbose)
{
    static_assert (SIZE_MAX >= UINT32_MAX);

    in.exceptions (istream::failbit | istream::badbit);
    uint32_t temp_number_variables;
    in >> temp_number_variables;
    number_variables = static_cast<size_t> (temp_number_variables);
    uint32_t temp_real_number_equations;
    in >> temp_real_number_equations;
    const size_t real_number_equations = static_cast<size_t> (temp_real_number_equations);
    number_equations = (real_number_equations < number_variables) ? number_variables :
                       real_number_equations;
    matrix.resize (number_equations);
    for (size_t i = 0; i < real_number_equations; ++i)
    {
        matrix.at (i).resize (number_variables + 1);
        for (auto& matrix_el : matrix.at (i))
        {
            string temp;
            in >> temp;
            matrix_el = parse_complex (temp);
        }
    }
    for (size_t i = real_number_equations; i < number_equations; ++i)
    {
        matrix.at (i).resize (number_variables + 1);
    }
    solution_partial.resize (number_variables);
    solution_general.resize (number_variables);
    solution_indexes.resize (number_variables);
    iota (solution_indexes.begin(), solution_indexes.end(), 0);
}

number_solutions solver::get_number_solutions()  const noexcept
{
    return number_solutions_;
}

size_t solver::get_size() const noexcept
{
    return matrix.size();
}

optional<vector<string>> solver::get_solution_general() const noexcept
{
    if (number_solutions_ != number_solutions::many)
    {
        return  nullopt;
    }

    return solution_general;
}

optional<vector<complex<double>>> solver::get_solution_partial() const noexcept
{
    if (number_solutions_ == number_solutions::none)
    {
        return  nullopt;
    }

    return solution_partial;
}

void solver::solve()
{
    if (verbose)
    {
        cout << "Start solving the equation.\n" << "Rows manipulation:" << endl;
    }
    gaus_first_step();
    if (number_solutions_ != number_solutions::none)
    {
        gaus_second_step();
        generate_solutions();
        check_that_solution_is_sane();
    }
    print_solution();
}

void solver::write_solution_to_file (const string& out_filename)
{
    ofstream out (out_filename);
    print_solution_internal (out);
    if (verbose)
    {
        cout << "Saved to file " << out_filename << endl;
    }
}

void solver::add_k_row1_to_row2 (complex<double>k, size_t row1, size_t row2)
{
    if (verbose)
    {
        cout << to_string (k) << " * R" << row1 + 1 << " +R" << row2 + 1 << " -> R" << row2 + 1 <<
             endl;
    }
    for (size_t i = 0; i < number_variables + 1; ++i)
    {
        matrix.at (row2).at (i) += k * matrix.at (row1).at (i);
    }
}

void solver::check_that_solution_is_sane()
{
    for (size_t i = number_variables; i < number_equations; ++i)
    {
        complex<double> sum (0.0, 0.0);
        for (size_t j = 0; j < number_variables; ++j)
        {
            sum += solution_partial.at (solution_indexes.at (j)) * matrix.at (i).at (solution_indexes.at (j));
        }
        if (!equals (sum, matrix.at (i).at (number_variables)))
        {
            number_solutions_ = number_solutions::none;
            return;
        }
    }
}

void solver::divide_row (size_t row, complex<double> k)
{
    if (verbose)
    {
        cout << "R" << row + 1 << " / " << to_string (k) << " -> R" << row + 1 << endl;
    }
    for (auto& matrix_el : matrix.at (row))
    {
        matrix_el /= k;
    }
}

void solver::gaus_first_step()
{
    for (size_t i = 0; i < number_variables; ++i)
    {
        if (equals (matrix.at (i).at (i), ZERO))
        {
            bool found_non_zero_element = false;
            for (size_t j = i + 1; j < number_equations; ++j)
            {
                if (!equals (matrix.at (j).at (i), ZERO))
                {
                    swap_rows (i, j);
                    found_non_zero_element = true;
                    break;
                }
            }
            if (!found_non_zero_element)
            {
                for (size_t j = i + 1; j < number_equations; ++j)
                {
                    if (!equals (matrix.at (i).at (j), ZERO))
                    {
                        swap_columns (i, j);
                        found_non_zero_element = true;
                        break;
                    }
                }
            }

            if (!found_non_zero_element)
            {
                for (size_t k = i + 1; !found_non_zero_element && k < number_variables; ++k)
                {
                    for (size_t j = i + 1; j < number_equations; ++j)
                    {
                        if (!equals (matrix.at (j).at (k), ZERO))
                        {
                            swap_columns (k, i);
                            swap_rows (j, i);
                            found_non_zero_element = true;
                            break;
                        }
                    }
                }
            }

            if (!found_non_zero_element)
            {
                if (equals (matrix.at (i).at (number_equations), ZERO))
                {
                    number_solutions_ = number_solutions::many;
                    continue;
                }
                else
                {
                    number_solutions_ = number_solutions::none;
                    return;
                }
            }
        }

        if (!equals (matrix.at (i).at (i), ONE))
        {
            divide_row (i, matrix.at (i).at (i));
        }
        for (size_t j = i + 1; j < number_equations && j < number_variables; ++j)
        {
            const complex<double> k = MINUS_ONE * matrix.at (j).at (i);
            if (!equals (k, ZERO))
            {
                add_k_row1_to_row2 (k, i, j);
            }
        }
    }
}

void solver::gaus_second_step()
{
    for (size_t i = number_variables - 1; true; --i)
    {
        if (i == 0)
        {
            break;
        }
        for (size_t j = i - 1; true; --j)
        {
            const complex<double> k = MINUS_ONE * matrix.at (j).at (i);
            if (!equals (k, ZERO))
            {
                add_k_row1_to_row2 (k, i, j);
            }
            if (j == 0)
            {
                break;
            }
        }
    }
}

void solver::generate_solutions()
{
    for (size_t i = 0; i < number_equations && i < number_variables; ++i)
    {
        auto& matrix_i = matrix.at (i);
        solution_partial.at (solution_indexes.at (i)) = matrix_i.at (number_variables);
        if (equals (matrix_i.at (i), ZERO))
        {
            solution_general.at (solution_indexes.at (i)) = "x" + std::to_string (solution_indexes.at (
                        i) + 1ULL);
        }
        else
        {
            solution_general.at (solution_indexes.at (i)) = to_string (matrix_i.at (number_variables));
            for (size_t j = i + 1; j < number_variables; ++j)
            {
                if (equals (matrix_i.at (j), ONE))
                {
                    solution_general.at (solution_indexes.at (i)) += " - x" + std::to_string (solution_indexes.at (
                                j) + 1ULL);
                }
                else if (!equals (matrix_i.at (j), ZERO))
                {
                    solution_general.at (solution_indexes.at (i)) += " - x" + std::to_string (solution_indexes.at (
                                j) + 1ULL) + " * (" + to_string (matrix_i.at (j)) + ")";
                }
            }
        }
    }
}

void solver::print_solution()
{
    if (verbose)
    {
        print_solution_internal (cout);
    }
}

void solver::print_solution_internal (ostream& out)
{
    switch (number_solutions_)
    {
    case number_solutions::none:
        out << "There are no solutions" << endl;
        break;
    case number_solutions::one:
        out << "(" << to_string (solution_partial.at (0));
        for (size_t i = 1; i < solution_partial.size(); ++i)
        {
            out << ", " << to_string (solution_partial.at (i));
        }
        out << ")" << endl;
        break;
    case number_solutions::many:
        out << "(" << solution_general.at (0);
        for (size_t i = 1; i < solution_partial.size(); ++i)
        {
            out << ", " << solution_general.at (i);
        }
        out << ")" << endl;
        break;
    default:
        assert (false);
        break;
    }
}

void solver::swap_columns (size_t column1, size_t column2)
{
    if (verbose)
    {
        cout << "C" << column1 + 1 << " <-> C" << column2 + 1 << endl;
    }
    const auto n = matrix.size();
    for (size_t i = 0; i < n; ++i)
    {
        swap (matrix.at (i).at (column1), matrix.at (i).at (column2));
    }
    swap (solution_indexes.at (column1), solution_indexes.at (column2));
}

void solver::swap_rows (size_t row1, size_t row2)
{
    if (verbose)
    {
        cout << "R" << row1 + 1 << " <-> R" << row2 + 1 << endl;
    }
    for (size_t i = 0; i < number_variables + 1; ++i)
    {
        swap (matrix.at (row1).at (i), matrix.at (row2).at (i));
    }
}
