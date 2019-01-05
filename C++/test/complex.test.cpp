// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include <boost/test/unit_test.hpp>

#include "complex.hpp"

using std::complex;
using std::string;
using std::exception;

BOOST_AUTO_TEST_CASE (complex_toString1)
{
    const complex<double> a (1.0, 0.0);
    const string expected ("1");
    const string actual = to_string (a);

    BOOST_CHECK_EQUAL (expected, actual);
}

BOOST_AUTO_TEST_CASE (complex_toString2)
{
    const complex<double> a (0.0, 1.0);
    const string expected ("1i");
    const string actual = to_string (a);

    BOOST_CHECK_EQUAL (expected, actual);
}

BOOST_AUTO_TEST_CASE (complex_toString3)
{
    const complex<double> a (-2.4, 1.5);
    const string expected ("-2.4+1.5i");
    const string actual = to_string (a);

    BOOST_CHECK_EQUAL (expected, actual);
}

BOOST_AUTO_TEST_CASE (complex_toString4)
{
    const complex<double> a (2.4, -1.5);
    const string expected ("2.4-1.5i");
    const string actual = to_string (a);

    BOOST_CHECK_EQUAL (expected, actual);
}

BOOST_AUTO_TEST_CASE (complex_toString5)
{
    const complex<double> a (0.0, 0.0);
    const string expected ("0");
    const string actual = to_string (a);

    BOOST_CHECK_EQUAL (expected, actual);
}

BOOST_AUTO_TEST_CASE (complex_equals1)
{
    const complex<double> a (1.0, 2.0);
    const complex<double> b (1.0, 2.0);

    BOOST_CHECK (equals (a, b));
}

BOOST_AUTO_TEST_CASE (complex_equals2)
{
    const complex<double> a (1.0, 2.0);
    const complex<double> b (1.1, 2.0);

    BOOST_CHECK (!equals (a, b));
}

BOOST_AUTO_TEST_CASE (complex_equals3)
{
    const complex<double> a (1.0, 2.1);
    const complex<double> b (1.0, 2.0);

    BOOST_CHECK (!equals (a, b));
}

BOOST_AUTO_TEST_CASE (complex_parse1)
{
    try
    {
        const complex<double>a = parse_complex ("gbc");
        (void)a;
    }
    catch (const exception&)
    {
        return;
    }
    BOOST_FAIL ("no throw exception");
}

BOOST_AUTO_TEST_CASE (complex_parse2)
{
    const complex<double> actual = parse_complex ("-1.3");
    const complex<double> expected (-1.3, 0.0);

    BOOST_CHECK (equals (actual, expected));
}

BOOST_AUTO_TEST_CASE (complex_parse3)
{
    const complex<double> actual = parse_complex ("2.5i");
    const complex<double> expected (0.0, 2.5);

    BOOST_CHECK (equals (actual, expected));
}

BOOST_AUTO_TEST_CASE (complex_parse4)
{
    const complex<double> actual = parse_complex ("1.3-2.5i");
    const complex<double> expected (1.3, -2.5);

    BOOST_CHECK (equals (actual, expected));
}

BOOST_AUTO_TEST_CASE (complex_parse5)
{
    const complex<double> actual = parse_complex ("1");
    const complex<double> expected (1.0, 0.0);

    BOOST_CHECK (equals (actual, expected));
}

BOOST_AUTO_TEST_CASE (complex_parse6)
{
    try
    {
        const complex<double>a = parse_complex ("1.3-2.5");
        (void)a;
    }
    catch (const exception&)
    {
        return;
    }
    BOOST_FAIL ("no throw exception");
}

BOOST_AUTO_TEST_CASE (complex_parse7)
{
    try
    {
        const complex<double>a = parse_complex ("1.3i-2.5");
        (void)a;
    }
    catch (const exception&)
    {
        return;
    }
    BOOST_FAIL ("no throw exception");
}
