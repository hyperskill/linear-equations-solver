// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include "complex.hpp"

#include <cmath>
#include <cstdio>
#include <exception>
#include <sstream>

using std::abs;
using std::complex;
using std::string;
using std::istringstream;
using std::ostringstream;
using std::runtime_error;
using std::round;

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
        imag = real;
        real = 0.0;
        break;
    case EOF:
        break;
    default:
        in.unget();
        in >> imag;
        const int last_character = in.get();
        if (last_character != 'i')
        {
            throw runtime_error ("can't parse complex");
        }
        break;
    }
    return complex<double> (real, imag);
}

bool equals (const complex<double>& a, const complex<double>& b)
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
