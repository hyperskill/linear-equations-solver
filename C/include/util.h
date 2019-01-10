#ifndef UTIL_H
#define UTIL_H

#pragma once

#include <stdbool.h>
#include <stdio.h>

#include <attributes.h>
#include <cpp_support.h>

BEGIN_DECLS

bool  NONNULL (1, 2) str_equal (const char* s1, const char* s2) noexcept;

FILE* NONNULL (1) generate_file ( const char* s ) noexcept;

END_DECLS

#endif
