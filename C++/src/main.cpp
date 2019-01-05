#include <iostream>
#include <fstream>
#include <exception>

#include "parameters.hpp"
#include "solver.hpp"

int main (int argc, const char* argv[])
{
    try
    {
        const parameters p (static_cast<std::size_t> (argc), argv);
        std::ifstream in (p.get_in());
        solver s (in, p.is_verbose());
        s.solve();
        s.write_solution_to_file (p.get_out());
    }
    catch (std::exception& e)
    {
        std::cerr << "An exception occurs " << e.what() << std::endl;
    }
}
