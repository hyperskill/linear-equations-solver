// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include "solver.h"

#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include <32bit_support.h>
#include <cpp_support.h>

#include "util.h"
#include "number_solutions.h"
#include "complex_support.h"


struct solver
{
    size_t number_equations;
    size_t number_variables;
    complex_double* matrix;
    complex_double* solution_partial;
    char** solution_general;
    size_t* solution_indexes;
    number_solutions number_solutions_;
    bool verbose;
    bool stack;
};

const size_t solver_size = sizeof (solver);

STATIC_CONST complex_double ZERO = CMPLX (0.0, 0.0);
STATIC_CONST complex_double ONE = CMPLX (1.0, 0.0);
STATIC_CONST complex_double MINUS_ONE = CMPLX (-1.0, 0.0);

static void add_k_row1_to_row2 (solver* self, complex_double k, size_t row1,
                                size_t row2);
static size_t get_matrix_index (const solver* self, size_t i,
                                size_t j) noexcept;
static void check_that_solution_is_sane (solver* self);
static void divide_row (solver* self, size_t row, complex_double k);
static void gaus_first_step (solver* self);
static void gaus_second_step (solver* self);
static bool generate_solutions (solver* self) noexcept;
static void print_solution (solver* self) noexcept;
static void print_solution_internal (solver* self, FILE* out) noexcept;
static void swap_columns (solver* self, size_t column1,
                          size_t column2) noexcept;
static void swap_rows (solver* self, size_t row1, size_t row2) noexcept;

solver* (solver_new) (FILE* in, bool verbose, void* memory)
{
    assert (in != nullptr);

    solver* self = (solver*)memory;
    if (self == nullptr)
    {
        self  = (solver*)calloc (1, sizeof (solver));
        if (self == nullptr)
        {
            return nullptr;
        }
        self->stack = false;
    }
    else
    {
        self->stack = true;
    }

    long long temp_number_variables = 0;
    long long temp_real_number_equations = 0;
    int scanf_result = fscanf (in, "%lld%lld", &temp_number_variables,
                               &temp_real_number_equations);
    if (scanf_result != 2 || temp_number_variables < 1 ||
            temp_real_number_equations < 1 ||
            temp_number_variables > UINT32_MAX ||
            temp_real_number_equations > UINT32_MAX)
    {
        solver_free (self);
        return nullptr;
    }
    const size_t real_number_equations = (size_t)temp_real_number_equations;
    self->number_variables = (size_t)temp_number_variables;
    self->number_equations = (real_number_equations < self->number_variables) ?
                             self->number_variables : real_number_equations;

    self->matrix = (complex_double*)calloc (self->number_equations *
                                            (self->number_variables + 1),
                                            sizeof (complex_double));
    self->solution_partial = (complex_double*)calloc (self->number_variables,
                             sizeof (complex_double));
    self->solution_general = (char**)calloc (self->number_variables,
                             sizeof (char*));
    self->solution_indexes = (size_t*)calloc (self->number_variables,
                             sizeof (size_t));
    if (self->matrix == nullptr || self->solution_partial == nullptr
            || self->solution_general == nullptr || self->solution_indexes == nullptr)
    {
        solver_free (self);
        return nullptr;
    }
    for (size_t i = 0; i < real_number_equations; ++i)
    {
        for (size_t j = 0; j < self->number_variables + 1; ++j)
        {
            char temp[4096] = {0};
            scanf_result = fscanf (in, "%4095s", temp);
            if (scanf_result != 1)
            {
                solver_free (self);
                return nullptr;
            }
            const bool parse_ok = complex_parse (&self->matrix[get_matrix_index (self, i,
                                                                  j)],
                                                 temp);
            if (!parse_ok)
            {
                solver_free (self);
                return nullptr;
            }
        }
    }

    for (size_t i = 0; i < self->number_variables; ++i)
    {
        self->solution_indexes[i] = i;
    }

    self->number_solutions_ = number_solutions_one;
    self->verbose = verbose;

    return self;
}

void solver_free (solver* self)
{
    if (self != nullptr)
    {
        free (self->matrix);
        free (self->solution_partial);
        free (self->solution_general);
        free (self->solution_indexes);
        if (!self->stack)
        {
            free (self);
        }
    }
}

number_solutions solver_get_number_solutions (const solver* self)
{
    assert (self != nullptr);
    return self->number_solutions_;
}

const char* const* solver_get_solution_general (solver* self,
        size_t* solution_general_size)
{
    assert (self != nullptr);
    assert (solution_general_size != nullptr);
    if (self->number_solutions_ != number_solutions_many)
    {
        return  nullptr;
    }
    *solution_general_size = self->number_variables;
    char* const* const tmp1 = (char* const * const)self->solution_general;
    const char* const* const tmp2 = (const char* const * const)tmp1;
    return tmp2;
}

const complex_double* solver_get_solution_partial (solver* self,
        size_t* solution_partial_size)
{
    assert (self != nullptr);
    assert (solution_partial_size != nullptr);
    if (self->number_solutions_ == number_solutions_none)
    {
        return  nullptr;
    }
    *solution_partial_size = self->number_variables;
    return self->solution_partial;
}

bool solver_solve (solver* self)
{
    assert (self != nullptr);
    if (self->verbose)
    {
        puts ("Start solving the equation.\nRows manipulation:");
    }
    gaus_first_step (self);
    if (self->number_solutions_ != number_solutions_none)
    {
        gaus_second_step (self);
        const bool ok = generate_solutions (self);
        if (!ok)
        {
            return false;
        }
        check_that_solution_is_sane (self);
    }
    print_solution (self);
    return true;
}
bool solver_write_solution_to_file (solver* self, const char* out_filename)
{
    assert (self != nullptr);
    assert (out_filename != nullptr);
    FILE* out = fopen (out_filename, "w");
    if (out == nullptr)
    {
        return false;
    }
    print_solution_internal (self, out);
    fclose (out);
    if (self->verbose)
    {
        printf ("Saved to file %s\n", out_filename);
    }
    return true;
}

static void add_k_row1_to_row2 (solver* self, complex_double k, size_t row1,
                                size_t row2)
{
    assert (self != nullptr);
    if (self->verbose)
    {
        printf ("%s * R%" PRIu32 " +R%" PRIu32 " -> R%" PRIu32 "\n",
                complex_to_string (k), CAST_SIZE_T_TO_UINT32 (row1 + 1),
                CAST_SIZE_T_TO_UINT32 (row2 + 1), CAST_SIZE_T_TO_UINT32 (row2 + 1));
    }
    for (size_t i = 0; i < self->number_variables + 1; ++i)
    {
        self->matrix[get_matrix_index (self, row2,
                                       i)] += k * self->matrix[get_matrix_index (self, row1, i)];
    }
}

static void check_that_solution_is_sane (solver* self)
{
    assert (self != nullptr);
    for (size_t i = self->number_variables; i < self->number_equations; ++i)
    {
        complex_double sum = CMPLX (0.0, 0.0);
        for (size_t j = 0; j < self->number_variables; ++j)
        {
            sum += self->solution_partial[self->solution_indexes[j]] *
                   self->matrix[get_matrix_index (self, i, j)];
        }
        if (!complex_equals (sum, self->matrix[get_matrix_index (self, i,
                                                                self->number_variables)]))
        {
            self->number_solutions_ = number_solutions_none;
            return;
        }
    }
}

static void divide_row (solver* self, size_t row, complex_double k)
{
    assert (self != nullptr);
    if (self->verbose)
    {
        printf ("R%" PRIu32 " / %s -> R%" PRIu32 "\n", CAST_SIZE_T_TO_UINT32 (row + 1),
                complex_to_string (k), CAST_SIZE_T_TO_UINT32 (row + 1));
    }
    for (size_t i = 0; i < self->number_variables + 1; ++i)
    {
        self->matrix[get_matrix_index (self, row, i)] /= k;
    }
}

static void gaus_first_step (solver* self)
{
    assert (self != nullptr);
    for (size_t i = 0; i < self->number_variables; ++i)
    {
        if (complex_equals (self->matrix[get_matrix_index (self, i, i)], ZERO))
        {
            bool found_non_zero_element = false;
            for (size_t j = i + 1; j < self->number_equations; ++j)
            {
                if (!complex_equals (self->matrix[get_matrix_index (self, j, i)], ZERO))
                {
                    swap_rows (self, i, j);
                    found_non_zero_element = true;
                    break;
                }
            }
            if (!found_non_zero_element)
            {
                for (size_t j = i + 1; j < self->number_equations; ++j)
                {
                    if (!complex_equals (self->matrix[get_matrix_index (self, i, j)], ZERO))
                    {
                        swap_columns (self, i, j);
                        found_non_zero_element = true;
                        break;
                    }
                }
            }

            if (!found_non_zero_element)
            {
                for (size_t k = i + 1; !found_non_zero_element
                        && k < self->number_variables; ++k)
                {
                    for (size_t j = i + 1; j < self->number_equations; ++j)
                    {
                        if (!complex_equals (self->matrix[get_matrix_index (self, j, k)], ZERO))
                        {
                            swap_columns (self, k, i);
                            swap_rows (self, j, i);
                            found_non_zero_element = true;
                            break;
                        }
                    }
                }
            }

            if (!found_non_zero_element)
            {
                if (complex_equals (self->matrix[get_matrix_index (self, i,
                                                                  self->number_equations)], ZERO))
                {
                    self->number_solutions_ = number_solutions_many;
                    continue;
                }
                else
                {
                    self->number_solutions_ = number_solutions_none;
                    return;
                }
            }
        }

        if (!complex_equals (self->matrix[get_matrix_index (self, i, i)], ONE))
        {
            divide_row (self, i, self->matrix[get_matrix_index (self, i, i)]);
        }
        for (size_t j = i + 1; j < self->number_equations
                && j < self->number_variables; ++j)
        {
            const complex_double k = MINUS_ONE * self->matrix[get_matrix_index (self, j,
                                                      i)];
            if (!complex_equals (k, ZERO))
            {
                add_k_row1_to_row2 (self, k, i, j);
            }
        }
    }
}

static void gaus_second_step (solver* self)
{
    assert (self != nullptr);
    for (size_t i = self->number_variables - 1; true; --i)
    {
        if (i == 0)
        {
            break;
        }
        for (size_t j = i - 1; true; --j)
        {
            const complex_double k = MINUS_ONE * self->matrix[get_matrix_index (self, j,
                                                      i)];
            if (!complex_equals (k, ZERO))
            {
                add_k_row1_to_row2 (self, k, i, j);
            }
            if (j == 0)
            {
                break;
            }
        }
    }
}


static size_t get_matrix_index (const solver* self, size_t i, size_t j) noexcept
{
    return i * (self->number_variables + 1) + j;
}


static bool generate_solutions (solver* self) noexcept
{
    assert (self != nullptr);
    static char buffer[8192] = {0};
    bool ok = true;
    for (size_t i = 0; i < self->number_equations
            && i < self->number_variables; ++i)
    {
        self->solution_partial[self->solution_indexes[i]] =
            self->matrix[get_matrix_index (self, i, self->number_variables)];
        free (self->solution_general[self->solution_indexes[i]]);
        if (complex_equals (self->matrix[get_matrix_index (self, i, i)], ZERO))
        {
            ok = snprintf (buffer, sizeof (buffer) - 1, "x%" PRIu64,
                           self->solution_indexes[i] + 1ULL) > 0;
            if (!ok)
            {
                return false;
            }
            self->solution_general[self->solution_indexes[i]] = (char*)calloc (strlen (
                        buffer) + 1, sizeof (char));
            if (self->solution_general[self->solution_indexes[i]] == nullptr)
            {
                return false;
            }
            strcpy (self->solution_general[self->solution_indexes[i]], buffer);
        }
        else
        {
            ok = snprintf (buffer, sizeof (buffer) - 1, "%s",
                           complex_to_string (self->matrix[get_matrix_index (self, i,
                                                               self->number_variables)])) > 0;
            self->solution_general[self->solution_indexes[i]] = (char*)calloc (strlen (
                        buffer) + 1, sizeof (char));
            if (self->solution_general[self->solution_indexes[i]] == nullptr)
            {
                return false;
            }
            strcpy (self->solution_general[self->solution_indexes[i]], buffer);
            for (size_t j = i + 1; j < self->number_variables; ++j)
            {
                if (complex_equals (self->matrix[get_matrix_index (self, i, j)], ONE))
                {
                    ok = snprintf (buffer, sizeof (buffer) - 1, " - x%" PRIu64,
                                   self->solution_indexes[j] + 1ULL) > 0;
                    if (!ok)
                    {
                        return false;
                    }
                    char* tmp = (char*)realloc (self->solution_general[self->solution_indexes[i]],
                                                strlen (self->solution_general[self->solution_indexes[i]]) + strlen (
                                                    buffer) + 1);
                    if (tmp == nullptr)
                    {
                        return false;
                    }
                    self->solution_general[self->solution_indexes[i]] = tmp;
                    strcat (self->solution_general[self->solution_indexes[i]], buffer);
                }
                else if (!complex_equals (self->matrix[get_matrix_index (self, i, j)], ZERO))
                {
                    ok = snprintf (buffer, sizeof (buffer) - 1, " - x%" PRIu64 " * (%s)",
                                   self->solution_indexes[j] + 1ULL,
                                   complex_to_string (self->matrix[get_matrix_index (self, i, j)])) > 0;
                    if (!ok)
                    {
                        return false;
                    }
                    char* tmp = (char*)realloc (self->solution_general[self->solution_indexes[i]],
                                                strlen (self->solution_general[self->solution_indexes[i]]) + strlen (
                                                    buffer) + 1);
                    if (tmp == nullptr)
                    {
                        return false;
                    }
                    self->solution_general[self->solution_indexes[i]] = tmp;
                    strcat (self->solution_general[self->solution_indexes[i]], buffer);
                }
            }
        }
    }
    return true;
}

static void print_solution (solver* self) noexcept
{
    assert (self != nullptr);
    if (self->verbose)
    {
        print_solution_internal (self, stdout);
    }
}

static void print_solution_internal (solver* self, FILE* out) noexcept
{
    assert (self != nullptr);
    assert (out != nullptr);
    switch (self->number_solutions_)
    {
    case number_solutions_none:
        fprintf (out, "There are no solutions\n");
        break;
    case number_solutions_one:
        fprintf (out, "(%s", complex_to_string (self->solution_partial[0]));
        for (size_t i = 1; i < self->number_variables; ++i)
        {
            fprintf (out, ", %s", complex_to_string (self->solution_partial[i]));
        }
        fprintf (out, ")\n");
        break;
    case number_solutions_many:
        fprintf (out, "(%s", self->solution_general[0]);
        for (size_t i = 1; i < self->number_variables; ++i)
        {
            fprintf (out, ", %s", self->solution_general[i]);
        }
        fprintf (out, ")\n");
        break;
    default:
        assert (false);
        break;
    }
}

static void swap_columns (solver* self, size_t column1, size_t column2) noexcept
{
    assert (self != nullptr);
    if (self->verbose)
    {
        printf ("C%" PRIu32 " <-> C%" PRIu32 "\n", CAST_SIZE_T_TO_UINT32 (column1 + 1),
                CAST_SIZE_T_TO_UINT32 (column2 + 1));
    }
    for (size_t i = 0; i < self->number_equations; ++i)
    {
        const complex_double tmp = self->matrix[get_matrix_index (self, i, column1)];
        self->matrix[get_matrix_index (self, i,
                                       column1)] = self->matrix[get_matrix_index (self, i, column2)];
        self->matrix[get_matrix_index (self, i, column2)] = tmp;
    }
    const size_t temp_index = self->solution_indexes[column1];
    self->solution_indexes[column1] = self->solution_indexes[column2];
    self->solution_indexes[column2] = temp_index;
}

static void swap_rows (solver* self, size_t row1, size_t row2) noexcept
{
    assert (self != nullptr);
    if (self->verbose)
    {
        printf ("R%" PRIu32 " <-> R%" PRIu32 "\n", CAST_SIZE_T_TO_UINT32 (row1 + 1),
                CAST_SIZE_T_TO_UINT32 (row2 + 1));
    }
    for (size_t i = 0; i < self->number_variables + 1; ++i)
    {
        const complex_double tmp = self->matrix[get_matrix_index (self, row1, i)];
        self->matrix[get_matrix_index (self, row1,
                                       i)] = self->matrix[get_matrix_index (self, row2, i)];
        self->matrix[get_matrix_index (self, row2, i)] = tmp;
    }
}
