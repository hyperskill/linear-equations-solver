#ifndef COMPLEX_SUPPORT_H
#define COMPLEX_SUPPORT_H

#pragma once

#include <useful-c-macros/all.h>

#include <stdbool.h>

BEGIN_DECLS

const char* complex_to_string (complex_double a);

bool CONST complex_equals (complex_double a, complex_double b);

bool complex_parse (complex_double* a, const char* s);

END_DECLS

#endif
