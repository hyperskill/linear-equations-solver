#ifndef COMPLEX_HPP
#define COMPLEX_HPP

#pragma once

#include <complex>
#include <string>

constexpr double EPSILON = 0.00001;

std::complex<double> parse_complex (const std::string& s);

bool equals (const std::complex<double>& a, const std::complex<double>& b) noexcept;

std::string to_string (const std::complex<double>& c);

#endif
