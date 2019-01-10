#ifndef UNIVERSAL_COMPLEX_H
#define UNIVERSAL_COMPLEX_H

#pragma once

#ifndef __cplusplus
#include <complex.h>
typedef _Complex double complex_double;
#ifndef CMPLX
#define CMPLX(x, y) ((complex_double)((double)(x) + I * (double)(y)))
#endif
#else
#include <complex>
using complex_double = std::complex<double>;
constexpr complex_double CMPLX(double x, double y)
{
    return complex_double(x, y);
}
constexpr double cimag(complex_double a)
{
    return a.imag();
}
constexpr double creal(complex_double a)
{
    return a.real();
}
#endif

#endif
