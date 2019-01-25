// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include "complex_support.h"

#include <assert.h>
#include <stdio.h>
#include <math.h>

#include "util.h"

static const double EPSILON = 0.00001;

const char* complex_to_string (complex_double a)
{
    int snprintf_result = 0;
    static char buffer[4096] = {0};
    const double real = round (creal (a) * 10000.0) / 10000.0;
    const double imag = round (cimag (a) * 10000.0) / 10000.0;
    if (fabs (imag) < EPSILON)
    {
        snprintf_result = snprintf (buffer, sizeof (buffer) - 1, "%g", real);
        assert (snprintf_result > 0);
    }
    else if (fabs (real) < EPSILON)
    {
        snprintf_result = snprintf (buffer, sizeof (buffer) - 1, "%gi", imag);
        assert (snprintf_result > 0);
    }
    else
    {
        const char* prefix = imag > 0 ? "+" : "";
        snprintf_result = snprintf (buffer, sizeof (buffer) - 1, "%g%s%gi", real,
                                    prefix, imag);
        assert (snprintf_result > 0);
    }

    return buffer;
}

bool complex_equals (complex_double a, complex_double b)
{
    return fabs (cimag (a) - cimag (b)) < EPSILON
           && fabs (creal (a) - creal (b)) < EPSILON;
}

bool complex_parse (complex_double* a, const char* s)
{
    assert (s != nullptr);
    assert (a != nullptr);

    double real = 0.0;
    double imag = 0.0;
    char c[3] = {0};
    int result = sscanf (s, "%lg%lg%2s", &real, &imag, c);
    if (result != 3)
    {
        real = 0.0;
        result = sscanf (s, "%lg%2s", &imag, c);
        if (result != 2)
        {
            c[0] = '\0';
            imag = 0.0;
            result = sscanf (s, "%lg", &real);
            if (result == 1)
            {
                c[0] = 'i';
                c[1] = '\0';
            }
            else
            {
                result = sscanf (s, "%2s", c);
                if (result == 1)
                {
                    if (str_equal (c, "i") || str_equal (c, "+i"))
                    {
                        imag = 1.0;
                        c[0] = 'i';
                        c[1] = '\0';
                    }
                    else if (str_equal (c, "-i"))
                    {
                        imag = -1.0;
                        c[0] = 'i';
                        c[1] = '\0';
                    }
                }
            }
        }
        else
        {
            if (str_equal (c, "+i"))
            {
                real = imag;
                imag = 1.0;
                c[0] = 'i';
                c[1] = '\0';
            }
            else if (str_equal (c, "-i"))
            {
                real = imag;
                imag = -1.0;
                c[0] = 'i';
                c[1] = '\0';
            }
        }
    }
    if (!str_equal (c, "i"))
    {
        return false;
    }
    *a =  CMPLX (real, imag);
    return true;
}
