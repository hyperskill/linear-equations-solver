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

using std::iota;
using std::istream;
using std::size_t;
using std::complex;
using std::nullopt;
using std::uint32_t;
using std::ifstream;

constexpr complex<double> ZERO (0.0, 0.0);
constexpr complex<double> ONE (1.0, 0.0);
constexpr complex<double> MINUS_ONE (-1.0, 0.0);

solver::solver (istream& in, bool verbose /* = false */)
    : number_solutions_ (number_solutions::one)
    , verbose (verbose)
{
    assert (SIZE_MAX >= UINT32_MAX);

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
            std::string temp;
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

std::size_t solver::get_size() const noexcept
{
    return matrix.size();
}

std::optional<std::vector<std::string>> solver::get_solution_general() const
{
    if (number_solutions_ != number_solutions::many)
    {
        return  std::nullopt;
    }

    return solution_general;
}

std::optional<std::vector<std::complex<double>>> solver::get_solution_partial() const
{
    if (number_solutions_ == number_solutions::none)
    {
        return  std::nullopt;
    }

    return solution_partial;
}

void solver::solve()
{
    if (verbose)
    {
        std::cout << "Start solving the equation.\n" << "Rows manipulation:" << std::endl;
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

void solver::write_solution_to_file (const std::string& out_filename)
{
    std::ofstream out (out_filename);
    print_solution_internal (out);
    if (verbose)
    {
        std::cout << "Saved to file " << out_filename << std::endl;
    }
}

void solver::add_k_row1_to_row2 (std::complex<double>k, std::size_t row1, std::size_t row2)
{
    if (verbose)
    {
        std::cout << to_string (k) << " * R" << row1 + 1 << " +R" << row2 + 1 << " -> R" << row2 + 1 <<
                  std::endl;
    }
    for (std::size_t i = 0; i < number_variables + 1; ++i)
    {
        matrix[row2][i] += k * matrix[row1][i];
    }
}

void solver::check_that_solution_is_sane()
{
    for (std::size_t i = number_variables; i < number_equations; ++i)
    {
        std::complex<double> sum (0.0, 0.0);
        for (std::size_t j = 0; j < number_variables; ++j)
        {
            sum += solution_partial[solution_indexes[j]] * matrix[i][solution_indexes[j]];
        }
        if (!equals (sum, matrix[i][number_variables]))
        {
            number_solutions_ = number_solutions::none;
            return;
        }
    }
}

void solver::divide_row (std::size_t row, std::complex<double> k)
{
    if (verbose)
    {
        std::cout << "R" << row + 1 << " / " << to_string (k) << " -> R" << row + 1 << std::endl;
    }
    for (auto& matrix_el : matrix[row])
    {
        matrix_el /= k;
    }
}

void solver::gaus_first_step()
{
    for (std::size_t i = 0; i < number_variables; ++i)
    {
        if (equals (matrix[i][i], ZERO))
        {
            bool found_non_zero_element = false;
            for (std::size_t j = i + 1; j < number_equations; ++j)
            {
                if (!equals (matrix[j][i], ZERO))
                {
                    swap_rows (i, j);
                    found_non_zero_element = true;
                    break;
                }
            }
            if (!found_non_zero_element)
            {
                for (std::size_t j = i + 1; j < number_equations; ++j)
                {
                    if (!equals (matrix[i][j], ZERO))
                    {
                        swap_columns (i, j);
                        found_non_zero_element = true;
                        break;
                    }
                }
            }

            if (!found_non_zero_element)
            {
                for (std::size_t k = i + 1; !found_non_zero_element && k < number_variables; ++k)
                {
                    for (std::size_t j = i + 1; j < number_equations; ++j)
                    {
                        if (!equals (matrix[j][k], ZERO))
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
                if (equals (matrix[i][number_equations], ZERO))
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

        if (!equals (matrix[i][i], ONE))
        {
            divide_row (i, matrix[i][i]);
        }
        for (std::size_t j = i + 1; j < number_equations && j < number_variables; ++j)
        {
            const std::complex<double> k = MINUS_ONE * matrix[j][i];
            if (!equals (k, ZERO))
            {
                add_k_row1_to_row2 (k, i, j);
            }
        }
    }
}

void solver::gaus_second_step()
{
    for (std::size_t i = number_variables - 1; true; --i)
    {
        if (i == 0)
        {
            break;
        }
        for (std::size_t j = i - 1; true; --j)
        {
            const std::complex<double> k = MINUS_ONE * matrix[j][i];
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
    for (std::size_t i = 0; i < number_equations && i < number_variables; ++i)
    {
        solution_partial[solution_indexes[i]] = matrix[i][number_variables];
        if (equals (matrix[i][i], ZERO))
        {
            solution_general[solution_indexes[i]] = "x" + std::to_string (solution_indexes[i] + 1ULL);
        }
        else
        {
            solution_general[solution_indexes[i]] = to_string (matrix[i][number_variables]);
            for (std::size_t j = i + 1; j < number_variables; ++j)
            {
                if (equals (matrix[i][j], ONE))
                {
                    solution_general[solution_indexes[i]] += " - x" + std::to_string (solution_indexes[j] + 1ULL);
                }
                else if (!equals (matrix[i][j], ZERO))
                {
                    solution_general[solution_indexes[i]] += " - x" + std::to_string (solution_indexes[j] + 1ULL)
                            + " * (" + to_string (matrix[i][j]) + ")";
                }
            }
        }
    }
}

void solver::print_solution()
{
    if (verbose)
    {
        print_solution_internal (std::cout);
    }
}

void solver::print_solution_internal (std::ostream& out)
{
    switch (number_solutions_)
    {
    case number_solutions::none:
        out << "There are no solutions" << std::endl;
        break;
    case number_solutions::one:
        out << "(" << to_string (solution_partial[0]);
        for (std::size_t i = 1; i < solution_partial.size(); ++i)
        {
            out << ", " << to_string (solution_partial[i]);
        }
        out << ")" << std::endl;
        break;
    case number_solutions::many:
        out << "(" << solution_general[0];
        for (std::size_t i = 1; i < solution_partial.size(); ++i)
        {
            out << ", " << solution_general[i];
        }
        out << ")" << std::endl;
        break;
    default:
        assert (false);
        break;
    }
}

void solver::swap_columns (std::size_t column1, std::size_t column2)
{
    if (verbose)
    {
        std::cout << "C" << column1 + 1 << " <-> C" << column2 + 1 << std::endl;
    }
    const auto n = matrix.size();
    for (std::size_t i = 0; i < n; ++i)
    {
        std::swap (matrix[i][column1], matrix[i][column2]);
    }
    std::swap (solution_indexes[column1], solution_indexes[column2]);
}

void solver::swap_rows (std::size_t row1, std::size_t row2)
{
    if (verbose)
    {
        std::cout << "R" << row1 + 1 << " <-> R" << row2 + 1 << std::endl;
    }
    for (std::size_t i = 0; i < number_variables + 1; ++i)
    {
        std::swap (matrix[row1][i], matrix[row2][i]);
    }
}
