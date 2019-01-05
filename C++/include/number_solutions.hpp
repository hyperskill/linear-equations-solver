#ifndef NUMBER_SOLUTIONS_HPP
#define NUMBER_SOLUTIONS_HPP

#pragma once

#include <cassert>
#include <iostream>

enum class number_solutions
{
    none,
    one,
    many
};

std::ostream& operator<< (std::ostream& out, number_solutions s);

#endif
