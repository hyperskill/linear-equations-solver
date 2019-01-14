// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include "complex.hpp"

#include <cmath>
#include <cstdio>
#include <exception>
#include <sstream>

#include "util.hpp"

using std::abs;
using std::complex;
using std::sscanf;
using std::string;
using std::istringstream;
using std::ostringstream;
using std::runtime_error;
#ifndef __DJGPP__
using std::round;
#endif

constexpr double EPSILON = 0.00001;

#ifdef _LIBCPP_VERSION
/* в libc++ по другому устроена логика чтения из стрима, чем в libstdc++ и стандартной библиотеки от Microsoft
*  подробнее: https://bugs.llvm.org/show_bug.cgi?id=17782
*/
complex<double> parse_complex (const string& s)
{
    double real;
    double imag;
    char c[3] = {0};
    int result = sscanf (s.c_str(), "%lg%lg%2s", &real, &imag, c);
    if (result != 3)
    {
        real = 0.0;
        result = sscanf (s.c_str(), "%lg%2s", &imag, c);
        if (result != 2)
        {
            c[0] = '\0';
            imag = 0.0;
            result = sscanf (s.c_str(), "%lg", &real);
            if (result == 1)
            {
                c[0] = 'i';
                c[1] = '\0';
            }
        }
    }
    if (!str_equal (c, "i"))
    {
        throw runtime_error ("can't parse complex");
    }
    return complex<double> (real, imag);
}
#else
complex<double> parse_complex (const string& s)
{
    double real = 0.0;
    double imag = 0.0;
    istringstream in (s);
    in.exceptions (istringstream::failbit | istringstream::badbit);
    in >> real;
    const int next_character = in.eof() ? EOF : in.get();
    switch (next_character)
    {
    case 'i':
        if (in.peek() != EOF)
        {
            throw runtime_error ("can't parse complex");
        }
        imag = real;
        real = 0.0;
        break;
    case EOF:
        break;
    default:
        in.unget();
        in >> imag;
        const int last_character = in.get();
        if (last_character != 'i' || in.peek() != EOF)
        {
            throw runtime_error ("can't parse complex");
        }
        break;
    }
    return complex<double> (real, imag);
}
#endif

bool equals (const complex<double>& a, const complex<double>& b) noexcept
{
    return abs (a.imag() - b.imag()) < EPSILON && abs (a.real() - b.real()) < EPSILON;
}

string to_string (const complex<double>& c)
{
    ostringstream ss;
    const double real = round (c.real() * 10000.0) / 10000.0;
    const double imag = round (c.imag() * 10000.0) / 10000.0;
    if (abs (imag) < EPSILON)
    {
        ss << real;
    }
    else if (abs (real) < EPSILON)
    {
        ss << imag << "i";
    }
    else
    {
        const char* prefix = imag > 0 ? "+" : "";
        ss << real << prefix << imag << "i";
    }

    return ss.str();
}
