// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include <cstdlib>
#include <iostream>
#include <fstream>
#include <exception>

#include "parameters.hpp"
#include "solver.hpp"

using std::cerr;
using std::ifstream;
using std::endl;
using std::exception;
using std::size_t;

int main (int argc, const char* argv[])
{
    try
    {
        const parameters p (static_cast<size_t> (argc), argv);
        ifstream in (p.get_in());
        solver s (in, p.is_verbose());
        s.solve();
        s.write_solution_to_file (p.get_out());
    }
    catch (const exception& e)
    {
        cerr << "An exception occurs " << e.what() << endl;
        return EXIT_FAILURE;
    }
    return EXIT_SUCCESS;
}
