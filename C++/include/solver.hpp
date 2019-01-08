#ifndef SOLVER_HPP
#define SOLVER_HPP

#pragma once

#include <complex>
#include <cstddef>
#include <istream>
#include <string>
#include <vector>
#include <optional>

#include "number_solutions.hpp"

class solver
{
private:
    std::size_t number_equations;
    std::size_t number_variables;
    number_solutions number_solutions_;
    std::vector < std::vector<std::complex<double>>> matrix;
    std::vector<std::complex<double>> solution_partial;
    std::vector <std::string> solution_general;
    std::vector<std::size_t> solution_indexes;
    bool verbose;
public:
    solver (std::istream& in, bool verbose = false);
    number_solutions get_number_solutions() const noexcept;
    std::optional<std::vector<std::string>> get_solution_general() const noexcept;
    std::optional<std::vector<std::complex<double>>> get_solution_partial() const noexcept;
    void solve();
    void write_solution_to_file (const std::string& out_filename);

private:
    void add_k_row1_to_row2 (std::complex<double>k, std::size_t row1, std::size_t row2);
    void check_that_solution_is_sane();
    void divide_row (std::size_t row, std::complex<double> k);
    void gaus_first_step();
    void gaus_second_step();
    void generate_solutions();
    void print_solution();
    void print_solution_internal (std::ostream& out);
    void swap_columns (std::size_t column1, std::size_t column2);
    void swap_rows (std::size_t row1, std::size_t row2);
};

#endif
