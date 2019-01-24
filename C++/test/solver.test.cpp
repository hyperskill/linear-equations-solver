// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include <boost/test/unit_test.hpp>

#include "complex.hpp"
#include "number_solutions.hpp"
#include "solver.hpp"

#include <algorithm>
#include <complex>
#include <sstream>
#include <vector>
#include <type_traits>

using std::complex;
using std::istringstream;
using std::size_t;
using std::string;
using std::exception;
using std::vector;
using std::all_of;
using std::optional;
using std::reference_wrapper;


BOOST_AUTO_TEST_CASE (types1)
{
    istringstream in ("1 1\n1 2");
    const solver s (in);

    const bool result = std::is_same<decltype (s.get_number_solutions()), number_solutions>::value;
    BOOST_CHECK (result);
}

BOOST_AUTO_TEST_CASE (types2)
{
    istringstream in ("1 1\n1 2");
    const solver s (in);

    const bool result =
        std::is_same<decltype (s.get_solution_general()), optional<reference_wrapper<const vector<string>>>>::value;
    BOOST_CHECK (result);
}

BOOST_AUTO_TEST_CASE (types3)
{
    istringstream in ("1 1\n1 2");
    const solver s (in);

    const bool result =
        std::is_same<decltype (s.get_solution_partial()), optional<reference_wrapper <const vector<complex<double>>>>>::value;
    BOOST_CHECK (result);
}


BOOST_AUTO_TEST_CASE (solver_constructor1)
{
    istringstream in ("1 1\n1 2");
    BOOST_CHECK_NO_THROW (const solver s (in));
}

BOOST_AUTO_TEST_CASE (solver_constructor2)
{
    istringstream in ("1 1\nab 2");
    try
    {
        const solver s (in);
        (void)s;
    }
    catch (const exception&)
    {
        return;
    }
    BOOST_FAIL ("no throw exception");
}

BOOST_AUTO_TEST_CASE (solver_constructor3)
{
    istringstream in ("-1 1\n2 2");
    try
    {
        const solver s (in);
        (void)s;
    }
    catch (const exception&)
    {
        return;
    }
    BOOST_FAIL ("no throw exception");
}

BOOST_AUTO_TEST_CASE (solver_constructor4)
{
    istringstream in ("1 -1\n2 2");
    try
    {
        const solver s (in);
        (void)s;
    }
    catch (const exception&)
    {
        return;
    }
    BOOST_FAIL ("no throw exception");
}

BOOST_AUTO_TEST_CASE (solver_constructor5)
{
    istringstream in ("1 1\n2");
    try
    {
        const solver s (in);
        (void)s;
    }
    catch (const exception&)
    {
        return;
    }
    BOOST_FAIL ("no throw exception");
}

BOOST_AUTO_TEST_CASE (solver_constructor6)
{
    istringstream in ("0 1\n2 2");
    try
    {
        const solver s (in);
        (void)s;
    }
    catch (const exception&)
    {
        return;
    }
    BOOST_FAIL ("no throw exception");
}

BOOST_AUTO_TEST_CASE (solver_constructor7)
{
    istringstream in ("1 0\n2 2");
    try
    {
        const solver s (in);
        (void)s;
    }
    catch (const exception&)
    {
        return;
    }
    BOOST_FAIL ("no throw exception");
}

BOOST_AUTO_TEST_CASE (solver_solve0)
{
    istringstream in ("1   1 \n\n 2 4");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = {complex<double> (2.0, 0.0)};
    const number_solutions expected_number_solutions = number_solutions::one;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve1)
{
    istringstream in ("1 1\n2 4");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (2.0, 0.0) };
    const number_solutions expected_number_solutions = number_solutions::one;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve2)
{
    istringstream in ("2 2\n1 2 3\n4 5 6");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (-1.0, 0.0), complex<double> (2.0, 0.0) };
    const number_solutions expected_number_solutions = number_solutions::one;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve3)
{
    istringstream in ("2 2\n4 5 7\n3 9 9");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (0.85714, 0.0), complex<double> (0.71429, 0.0) };
    const number_solutions expected_number_solutions = number_solutions::one;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve4)
{
    istringstream in ("3 3\n1 1 2 9\n2 4 -3 1\n3 6 -5 0");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (1.0, 0.0), complex<double> (2.0, 0.0),
                                                                complex<double> (3.0, 0.0)
                                                              };
    const number_solutions expected_number_solutions = number_solutions::one;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_CHECK (!actual_general_solution.has_value());
}


BOOST_AUTO_TEST_CASE (solver_solve5)
{
    istringstream in ("2 2\n0 1 1\n1 0 1");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (1.0, 0.0), complex<double> (1.0, 0.0) };
    const number_solutions expected_number_solutions = number_solutions::one;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve6)
{
    istringstream in ("2 2\n0 1 1\n0 2 2");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (0.0, 0.0), complex<double> (1.0, 0.0) };
    const vector<string> expected_general_solution = { "x1", "1" };
    const number_solutions expected_number_solutions = number_solutions::many;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_REQUIRE (actual_general_solution.has_value());
    BOOST_CHECK_EQUAL_COLLECTIONS (expected_general_solution.begin(), expected_general_solution.end(),
                                   actual_general_solution->get().begin(), actual_general_solution->get().end());
}

BOOST_AUTO_TEST_CASE (solver_solve7)
{
    istringstream in ("2 2\n0 1 1\n0 2 3");
    solver s (in);
    s.solve();

    const number_solutions expected_number_solutions = number_solutions::none;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_CHECK (!actual_partial_solution.has_value());
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve8)
{
    istringstream in ("3 4\n0 1 2 9\n0 1 3 1\n1 0 6 0\n2 0 2 0");
    solver s (in);
    s.solve();

    const number_solutions expected_number_solutions = number_solutions::none;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_CHECK (!actual_partial_solution.has_value());
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve9)
{
    istringstream in ("3 1\n1 1 2 9");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (9.0, 0.0), complex<double> (0.0, 0.0),
                                                                complex<double> (0.0, 0.0)
                                                              };
    const vector<string> expected_general_solution = { "9 - x2 - x3 * (2)", "x2", "x3" };
    const number_solutions expected_number_solutions = number_solutions::many;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_REQUIRE (actual_general_solution.has_value());
    BOOST_CHECK_EQUAL_COLLECTIONS (expected_general_solution.begin(), expected_general_solution.end(),
                                   actual_general_solution->get().begin(), actual_general_solution->get().end());
}

BOOST_AUTO_TEST_CASE (solver_solve10)
{
    istringstream in ("4 4\n1 0 0 5 0\n0 0 0 0 0\n0 0 1 4 6\n0 0 5 5 5");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (-8.3333333, 0.0), complex<double> (0.0, 0.0),
                                                                complex<double> (-0.6666667, 0.0), complex<double> (1.6666667, 0.0)
                                                              };
    const vector<string> expected_general_solution = { "-8.3333", "x2", "-0.6667", "1.6667" };
    const number_solutions expected_number_solutions = number_solutions::many;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_REQUIRE (actual_general_solution.has_value());
    BOOST_CHECK_EQUAL_COLLECTIONS (expected_general_solution.begin(), expected_general_solution.end(),
                                   actual_general_solution->get().begin(), actual_general_solution->get().end());
}

BOOST_AUTO_TEST_CASE (solver_solve11)
{
    istringstream in ("4 4\n2 3 -1 1 1\n8 12 -9 8 3\n4 6 3 -2 3\n2 3 9 -7 3");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (0.6, 0.0), complex<double> (0.0, 0.0),
                                                                complex<double> (0.2, 0.0), complex<double> (0.0, 0.0)
                                                              };
    const vector<string> expected_general_solution = { "0.6 - x2 * (1.5) - x4 * (0.1)", "x2", "0.2 - x4 * (-0.8)", "x4" };
    const number_solutions expected_number_solutions = number_solutions::many;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_REQUIRE (actual_general_solution.has_value());
    BOOST_CHECK_EQUAL_COLLECTIONS (expected_general_solution.begin(), expected_general_solution.end(),
                                   actual_general_solution->get().begin(), actual_general_solution->get().end());
}

BOOST_AUTO_TEST_CASE (solver_solve12)
{
    istringstream
    in ("3 3\n1+2i -1.5-1.1i 2.12 91+5i\n-1+3i 1.2+3.5i -3.3 1+15i\n12.31 1.3-5i 12.3i -78.3i");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (6.73335286, -22.99754223),
                                                                complex<double> (-1.7976071, 2.08404919), complex<double> (15.69938581, 7.3960106)
                                                              };
    const number_solutions expected_number_solutions = number_solutions::one;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve13)
{
    istringstream in ("1\t1\t2\t4");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (2.0, 0.0) };
    const number_solutions expected_number_solutions = number_solutions::one;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve14)
{
    istringstream in ("1 1\r\n2 4");
    solver s (in);
    s.solve();

    const vector<complex<double>> expected_partial_solution = { complex<double> (2.0, 0.0) };
    const number_solutions expected_number_solutions = number_solutions::one;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_REQUIRE (actual_partial_solution.has_value());
    BOOST_REQUIRE (actual_partial_solution->get().size() == expected_partial_solution.size());
    const bool equals_partial = all_of (expected_partial_solution.cbegin(),
                                        expected_partial_solution.cend(), [&, i = size_t (0)] (auto&)mutable
    {
        const bool result = equals (expected_partial_solution.at (i), actual_partial_solution->get().at (i));
        ++i;
        return result;
    });
    BOOST_CHECK (equals_partial);
    BOOST_CHECK (!actual_general_solution.has_value());
}

BOOST_AUTO_TEST_CASE (solver_solve15)
{
    istringstream in ("3 4\n1 1 2 9\n0 1 3 1\n0 2 6 1\n0 0 0 0");
    solver s (in);
    s.solve();

    const number_solutions expected_number_solutions = number_solutions::none;

    const auto actual_partial_solution = s.get_solution_partial();
    const auto actual_number_solutions = s.get_number_solutions();
    const auto actual_general_solution = s.get_solution_general();

    BOOST_CHECK_EQUAL (expected_number_solutions, actual_number_solutions);
    BOOST_CHECK (!actual_partial_solution.has_value());
    BOOST_CHECK (!actual_general_solution.has_value());
}
