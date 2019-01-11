// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include <unity_fixture.h>

#include "solver.h"
#include "util.h"
#include "complex_support.h"

TEST_GROUP (Solver);


TEST_SETUP (Solver)
{
}

TEST_TEAR_DOWN (Solver)
{
}


TEST (Solver, constructor1)
{
    FILE* in = generate_file ("1 1\n1 2");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);

    TEST_ASSERT_NOT_NULL (s);

    solver_free (s);
    fclose (in);
}

TEST (Solver, constructor2)
{
    FILE* in = generate_file ("1 1\nnab 2");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);

    TEST_ASSERT_NULL (s);

    solver_free (s);
    fclose (in);
}

TEST (Solver, constructor3)
{
    FILE* in = generate_file ("-1 1\n2 2");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);

    TEST_ASSERT_NULL (s);

    solver_free (s);
    fclose (in);
}

TEST (Solver, constructor4)
{
    FILE* in = generate_file ("1 -1\n2 2");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);

    TEST_ASSERT_NULL (s);

    solver_free (s);
    fclose (in);
}

TEST (Solver, constructor5)
{
    FILE* in = generate_file ("1 1\n2");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);

    TEST_ASSERT_NULL (s);

    solver_free (s);
    fclose (in);
}

TEST (Solver, constructor6)
{
    FILE* in = generate_file ("0 1\n2 2");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);

    TEST_ASSERT_NULL (s);

    solver_free (s);
    fclose (in);
}

TEST (Solver, constructor7)
{
    FILE* in = generate_file ("1 0\n2 2");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);

    TEST_ASSERT_NULL (s);

    solver_free (s);
    fclose (in);
}

TEST (Solver, constructor8)
{
#ifdef __cplusplus
    uint8_t* const stack = (uint8_t*)alloca (solver_size);
#else
    alignas (max_align_t) uint8_t stack[solver_size];
#endif
    FILE* in = generate_file ("1 1\n1 2");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in, false, stack);

    TEST_ASSERT_NOT_NULL (s);

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve0)
{
    FILE* in = generate_file ("1   1 \n\n 2 4");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (2.0, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_one;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve1)
{
    FILE* in = generate_file ("1 1 \n2 4");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (2.0, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_one;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve2)
{
    FILE* in = generate_file ("2 2\n1 2 3\n4 5 6");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (-1.0, 0.0), CMPLX (2.0, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_one;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve3)
{
    FILE* in = generate_file ("2 2\n4 5 7\n3 9 9");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (0.85714, 0.0), CMPLX (0.71429, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_one;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve4)
{
    FILE* in = generate_file ("3 3\n1 1 2 9\n2 4 -3 1\n3 6 -5 0");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (1.0, 0.0), CMPLX (2.0, 0.0), CMPLX (3.0, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_one;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve5)
{
    FILE* in = generate_file ("2 2\n0 1 1\n1 0 1");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (1.0, 0.0), CMPLX (1.0, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_one;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve6)
{
    FILE* in = generate_file ("2 2\n0 1 1\n0 2 2");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (0.0, 0.0), CMPLX (1.0, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const char* expected_general_solution[] = { "x1", "1" };
    const size_t expected_general_solution_size = sizeof (
                expected_general_solution) / sizeof (expected_general_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_many;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NOT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }
    TEST_ASSERT_EQUAL (expected_general_solution_size,
                       actual_general_solution_size);
    TEST_ASSERT_EQUAL_STRING_ARRAY (actual_general_solution,
                                    expected_general_solution, actual_general_solution_size);

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve7)
{
    FILE* in = generate_file ("2 2\n0 1 1\n0 2 3");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const number_solutions expected_number_solutions = number_solutions_none;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve8)
{
    FILE* in = generate_file ("3 4\n0 1 2 9\n0 1 3 1\n1 0 6 0\n2 0 2 0");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const number_solutions expected_number_solutions = number_solutions_none;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve9)
{
    FILE* in = generate_file ("3 1\n1 1 2 9");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (9.0, 0.0), CMPLX (0.0, 0.0), CMPLX (0.0, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const char* expected_general_solution[] = { "9 - x2 - x3 * (2)", "x2", "x3" };
    const size_t expected_general_solution_size = sizeof (
                expected_general_solution) / sizeof (expected_general_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_many;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NOT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }
    TEST_ASSERT_EQUAL (expected_general_solution_size,
                       actual_general_solution_size);
    TEST_ASSERT_EQUAL_STRING_ARRAY (actual_general_solution,
                                    expected_general_solution, actual_general_solution_size);

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve10)
{
    FILE* in = generate_file ("4 4\n1 0 0 5 0\n0 0 0 0 0\n0 0 1 4 6\n0 0 5 5 5");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (-8.3333333, 0.0), CMPLX (0.0, 0.0), CMPLX (-0.6666667, 0.0), CMPLX (1.6666667, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const char* expected_general_solution[] = { "-8.3333", "x2", "-0.6667", "1.6667" };
    const size_t expected_general_solution_size = sizeof (
                expected_general_solution) / sizeof (expected_general_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_many;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NOT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }
    TEST_ASSERT_EQUAL (expected_general_solution_size,
                       actual_general_solution_size);
    TEST_ASSERT_EQUAL_STRING_ARRAY (actual_general_solution,
                                    expected_general_solution, actual_general_solution_size);

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve11)
{
    FILE* in =
        generate_file ("4 4\n2 3 -1 1 1\n8 12 -9 8 3\n4 6 3 -2 3\n2 3 9 -7 3");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (0.6, 0.0), CMPLX (0.0, 0.0), CMPLX (0.2, 0.0), CMPLX (0.0, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const char* expected_general_solution[] = { "0.6 - x2 * (1.5) - x4 * (0.1)", "x2", "0.2 - x4 * (-0.8)", "x4" };
    const size_t expected_general_solution_size = sizeof (
                expected_general_solution) / sizeof (expected_general_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_many;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NOT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }
    TEST_ASSERT_EQUAL (expected_general_solution_size,
                       actual_general_solution_size);
    TEST_ASSERT_EQUAL_STRING_ARRAY (actual_general_solution,
                                    expected_general_solution, actual_general_solution_size);

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve12)
{
    FILE* in =
        generate_file ("3 3\n1+2i -1.5-1.1i 2.12 91+5i\n-1+3i 1.2+3.5i -3.3 1+15i\n12.31 1.3-5i 12.3i -78.3i");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (6.73335286, -22.99754223), CMPLX (-1.7976071, 2.08404919), CMPLX (15.69938581, 7.3960106)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_one;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }

    solver_free (s);
    fclose (in);
}

TEST (Solver, solve13)
{
    FILE* in = generate_file ("1\t1\t2\t4");
    TEST_ASSERT_NOT_NULL (in);
    solver* s = solver_new (in);
    TEST_ASSERT_NOT_NULL (s);
    solver_solve (s);

    const complex_double expected_partial_solution[] = {CMPLX (2.0, 0.0)};
    const size_t expected_partial_solution_size = sizeof (
                expected_partial_solution) / sizeof (expected_partial_solution[0]);
    const number_solutions expected_number_solutions = number_solutions_one;

    size_t actual_partial_solution_size = 0;
    const complex_double* actual_partial_solution = solver_get_solution_partial (s,
            &actual_partial_solution_size);
    const number_solutions actual_number_solutions = solver_get_number_solutions (
                s);
    size_t actual_general_solution_size = 0;
    const char* const* actual_general_solution = solver_get_solution_general (s,
            &actual_general_solution_size);

    TEST_ASSERT_NOT_NULL (actual_partial_solution);
    TEST_ASSERT_NULL (actual_general_solution);
    TEST_ASSERT_EQUAL (expected_number_solutions, actual_number_solutions);
    TEST_ASSERT_EQUAL (expected_partial_solution_size,
                       actual_partial_solution_size);
    for (size_t i = 0; i < actual_partial_solution_size; ++i)
    {
        TEST_ASSERT (complex_equals (actual_partial_solution[i],
                                     expected_partial_solution[i]));
    }

    solver_free (s);
    fclose (in);
}
