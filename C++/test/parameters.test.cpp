// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include <boost/test/unit_test.hpp>

#include "parameters.hpp"

using std::size_t;

BOOST_AUTO_TEST_CASE (parameters_default_parameters)
{
    const size_t argc = 1;
    const char* argv[argc] = {"main.exe"};
    const parameters p (argc, argv);

    const char* expected_in = "input.txt";
    const char* expected_out = "output.txt";
    const bool expected_verbose = false;

    BOOST_CHECK_EQUAL (expected_in, p.get_in());
    BOOST_CHECK_EQUAL (expected_out, p.get_out());
    BOOST_CHECK_EQUAL (expected_verbose, p.is_verbose());
}

BOOST_AUTO_TEST_CASE (parameters_in)
{
    const size_t argc = 2;
    const char* argv[argc] = { "-in", "in.txt" };
    const parameters p (argc, argv);

    const char* expected_in = "in.txt";

    BOOST_CHECK_EQUAL (expected_in, p.get_in());
}

BOOST_AUTO_TEST_CASE (parameters_out)
{
    const size_t argc = 2;
    const char* argv[argc] = { "-out", "out2.txt" };
    const parameters p (argc, argv);

    const char* expected_out = "out2.txt";

    BOOST_CHECK_EQUAL (expected_out, p.get_out());
}

BOOST_AUTO_TEST_CASE (parameters_verbose)
{
    const size_t argc = 1;
    const char* argv[argc] = { "-verbose" };
    const parameters p (argc, argv);

    const bool expected_verbose = true;

    BOOST_CHECK_EQUAL (expected_verbose, p.is_verbose());
}
