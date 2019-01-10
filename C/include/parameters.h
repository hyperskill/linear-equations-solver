#ifndef PARAMETERS_H
#define PARAMETERS_H

#pragma once

#include <attributes.h>
#include <cpp_support.h>
#include <default_parameters.h>

#include <stdbool.h>
#include <stddef.h>

BEGIN_DECLS

typedef struct parameters parameters;

extern const size_t parameters_size;

#define _parameters_new_default(argc, argv, memory, ...) (parameters_new)(argc, argv, memory)
#define parameters_new(...)  EXPAND(_parameters_new_default(__VA_ARGS__, nullptr, 0))

parameters* NONNULL (2) (parameters_new) (size_t argc, const char* const* argv,
        void* memory) noexcept;
void parameters_free (parameters* p);
const char* NONNULL (1) parameters_get_in (const parameters* p);
const char* NONNULL (1) parameters_get_out (const parameters* p);
bool NONNULL (1) parameters_get_verbose (const parameters* p);

END_DECLS

#endif
