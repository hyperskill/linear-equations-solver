// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include <unity_fixture.h>

#include "parameters.h"

TEST_GROUP (Parameters);


TEST_SETUP (Parameters)
{
}

TEST_TEAR_DOWN (Parameters)
{
}

TEST (Parameters, new_stack)
{
#ifdef __cplusplus
    uint8_t* const stack = (uint8_t*)alloca (parameters_size);
#else
    alignas (max_align_t) uint8_t stack[parameters_size];
#endif
    const char* const argv[] = {""};
    const size_t argc = sizeof (argv) / sizeof (argv[0]);
    parameters* p = parameters_new (argc, argv, stack);

    TEST_ASSERT (p != nullptr);
    TEST_ASSERT_EQUAL_STRING ("input.txt", parameters_get_in (p));
    TEST_ASSERT_EQUAL_STRING ("output.txt", parameters_get_out (p));
    TEST_ASSERT_FALSE (parameters_get_verbose (p));

    parameters_free (p);
}

TEST (Parameters, default_parameters)
{
    const char* const argv[] = {""};
    const size_t argc = sizeof (argv) / sizeof (argv[0]);
    parameters* p = parameters_new (argc, argv);

    TEST_ASSERT (p != nullptr);
    TEST_ASSERT_EQUAL_STRING ("input.txt", parameters_get_in (p));
    TEST_ASSERT_EQUAL_STRING ("output.txt", parameters_get_out (p));
    TEST_ASSERT_FALSE (parameters_get_verbose (p));

    parameters_free (p);
}

TEST (Parameters, in)
{
    const char* const argv[] = {"-in", "in.txt"};
    const size_t argc = sizeof (argv) / sizeof (argv[0]);
    parameters* p = parameters_new (argc, argv);

    TEST_ASSERT (p != nullptr);
    TEST_ASSERT_EQUAL_STRING ("in.txt", parameters_get_in (p));

    parameters_free (p);
}

TEST (Parameters, out)
{
    const char* const argv[] = {"-out", "out.txt"};
    const size_t argc = sizeof (argv) / sizeof (argv[0]);
    parameters* p = parameters_new (argc, argv);

    TEST_ASSERT (p != nullptr);
    TEST_ASSERT_EQUAL_STRING ("out.txt", parameters_get_out (p));

    parameters_free (p);
}

TEST (Parameters, verbose)
{
    const char* const argv[] = {"-verbose"};
    const size_t argc = sizeof (argv) / sizeof (argv[0]);
    parameters* p = parameters_new (argc, argv);

    TEST_ASSERT (p != nullptr);
    TEST_ASSERT (parameters_get_verbose (p));

    parameters_free (p);
}

