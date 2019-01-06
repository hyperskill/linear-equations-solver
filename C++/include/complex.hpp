#ifndef COMPLEX_HPP
#define COMPLEX_HPP

#pragma once

#include <complex>
#include <string>

std::complex<double> parse_complex (const std::string& s);

bool equals (const std::complex<double>& a, const std::complex<double>& b) noexcept;

std::string to_string (const std::complex<double>& c);

#endif
