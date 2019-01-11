#ifndef SOLVER_H
#define SOLVER_H

#pragma once

#include <useful-c-macros\all.h>

#include <stdbool.h>
#include <stddef.h>
#include <stdio.h>

#include "number_solutions.h"

BEGIN_DECLS

typedef struct solver solver;

extern const size_t solver_size;

#define solver_new_2(...) (solver_new)(__VA_ARGS__, nullptr)
#define solver_new_1(...) solver_new_2(__VA_ARGS__, false)
#define solver_new(...)  EXPAND(VAR_FUNC3(__VA_ARGS__, (solver_new), solver_new_2, solver_new_1, nullptr))(__VA_ARGS__)
solver* NONNULL (1) (solver_new) (FILE* in, bool verbose, void* memory);
void solver_free (solver* self);
number_solutions solver_get_number_solutions (const solver* self);
const char* const* solver_get_solution_general (solver* self,
        size_t* solution_general_size);
const complex_double* solver_get_solution_partial (solver* self,
        size_t* solution_partial_size);
bool solver_solve (solver* self);
bool solver_write_solution_to_file (solver* self, const char* out_filename);

END_DECLS

#endif
