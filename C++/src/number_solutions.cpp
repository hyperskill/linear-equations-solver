// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include "number_solutions.hpp"

std::ostream& operator<< (std::ostream& out, number_solutions s)
{
    switch (s)
    {
    case number_solutions::none:
        out << "number_solutions::none";
        break;
    case number_solutions::one:
        out << "number_solutions::one";
        break;
    case number_solutions::many:
        out << "number_solutions::many";
        break;
    default:
        assert (false);
        break;
    }

    return out;
}
