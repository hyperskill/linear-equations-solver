#include <stdio.h>
#include <stdlib.h>
#include "parameters.h"
#include "solver.h"

int main (int argc, char* argv[])
{
#ifdef __cplusplus
    uint8_t* const parameters_stack = (uint8_t*)alloca (parameters_size);
    uint8_t* const solver_stack = (uint8_t*)alloca (solver_size);
#elif (__STDC_VERSION__ < 201112L)
    uint8_t* const parameters_stack = nullptr;
    uint8_t* const solver_stack = nullptr;
#else
    alignas (max_align_t) uint8_t parameters_stack[parameters_size];
    alignas (max_align_t) uint8_t solver_stack[solver_size];
#endif
    const size_t correct_argc = (size_t)argc;
    const char* const* correct_argv = (const char* const*) ((char* const*)argv);
    parameters* p = parameters_new (correct_argc, correct_argv, parameters_stack);
    if (p == nullptr)
    {
        fprintf (stderr, "can't read command line parameters\n");
        return EXIT_FAILURE;
    }
    FILE* in = fopen (parameters_get_in (p), "r");
    if (in == nullptr)
    {
        fprintf (stderr, "can't open -in file: %s\n", parameters_get_in (p));
        parameters_free (p);
        return EXIT_FAILURE;
    }
    solver* s = solver_new (in, parameters_get_verbose (p), solver_stack);
    if (s == nullptr)
    {
        fprintf (stderr, "can't read from -in file or incorrect data\n");
        parameters_free (p);
        fclose (in);
        return EXIT_FAILURE;
    }
    bool ok = solver_solve (s);
    if (!ok)
    {
        fprintf (stderr, "something wrong while solve\n");
        solver_free (s);
        parameters_free (p);
        fclose (in);
        return EXIT_FAILURE;
    }
    ok = solver_write_solution_to_file (s, parameters_get_out (p));
    if (!ok)
    {
        fprintf (stderr, "can't write to output file: %s\n", parameters_get_out (p));
        solver_free (s);
        parameters_free (p);
        fclose (in);
        return EXIT_FAILURE;
    }
    solver_free (s);
    parameters_free (p);
    fclose (in);
    return EXIT_SUCCESS;
}
