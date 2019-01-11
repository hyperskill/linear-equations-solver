#ifndef PARAMETERS_H
#define PARAMETERS_H

#pragma once

#include <useful-c-macros/all.h>

#include <stdbool.h>
#include <stddef.h>

BEGIN_DECLS

typedef struct parameters parameters;

extern const size_t parameters_size;

#define parameters_new_2(...) (parameters_new)(__VA_ARGS__, nullptr)
#define parameters_new(...)  EXPAND(VAR_FUNC3(__VA_ARGS__, (parameters_new), parameters_new_2, nullptr))(__VA_ARGS__)
parameters* NONNULL (2) (parameters_new) (size_t argc, const char* const* argv,
        void* memory) noexcept;
void parameters_free (parameters* p);
const char* PURE NONNULL (1) parameters_get_in (const parameters* p);
const char* PURE NONNULL (1) parameters_get_out (const parameters* p);
bool PURE NONNULL (1) parameters_get_verbose (const parameters* p);

END_DECLS

#endif
