// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include <unity_fixture.h>

#include "complex_support.h"

TEST_GROUP (ComplexSupport);


TEST_SETUP (ComplexSupport)
{
}

TEST_TEAR_DOWN (ComplexSupport)
{
}

TEST (ComplexSupport, to_string1)
{
    const complex_double a = CMPLX (1.0, 0.0);
    const char* expected = "1";
    const char* actual = complex_to_string (a);

    TEST_ASSERT (actual != nullptr);
    TEST_ASSERT_EQUAL_STRING (expected, actual);
}

TEST (ComplexSupport, to_string2)
{
    const complex_double a = CMPLX (0.0, 1.0);
    const char* expected = "1i";
    const char* actual = complex_to_string (a);

    TEST_ASSERT (actual != nullptr);
    TEST_ASSERT_EQUAL_STRING (expected, actual);
}

TEST (ComplexSupport, to_string3)
{
    const complex_double a = CMPLX (-2.4, 1.5);
    const char* expected = "-2.4+1.5i";
    const char* actual = complex_to_string (a);

    TEST_ASSERT (actual != nullptr);
    TEST_ASSERT_EQUAL_STRING (expected, actual);
}

TEST (ComplexSupport, to_string4)
{
    const complex_double a = CMPLX (2.4, -1.5);
    const char* expected = "2.4-1.5i";
    const char* actual = complex_to_string (a);

    TEST_ASSERT (actual != nullptr);
    TEST_ASSERT_EQUAL_STRING (expected, actual);
}

TEST (ComplexSupport, to_string5)
{
    const complex_double a = CMPLX (0.0, 0.0);
    const char* expected = "0";
    const char* actual = complex_to_string (a);

    TEST_ASSERT (actual != nullptr);
    TEST_ASSERT_EQUAL_STRING (expected, actual);
}

TEST (ComplexSupport, equals1)
{
    const complex_double a = CMPLX (1.0, 2.0);
    const complex_double b = CMPLX (1.0, 2.0);

    TEST_ASSERT (complex_equals (a, b));
}

TEST (ComplexSupport, equals2)
{
    const complex_double a = CMPLX (1.0, 2.0);
    const complex_double b = CMPLX (1.1, 2.0);

    TEST_ASSERT_FALSE (complex_equals (a, b));
}

TEST (ComplexSupport, equals3)
{
    const complex_double a = CMPLX (1.0, 2.1);
    const complex_double b = CMPLX (1.0, 2.0);

    TEST_ASSERT_FALSE (complex_equals (a, b));
}

TEST (ComplexSupport, parse1)
{
    complex_double a = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&a, "gdc");

    TEST_ASSERT_FALSE (ok);
}

TEST (ComplexSupport, parse2)
{
    const complex_double expected = CMPLX (-1.3, 0.0);
    complex_double actual = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&actual, "-1.3");

    TEST_ASSERT (ok);
    TEST_ASSERT (complex_equals (expected, actual));
}

TEST (ComplexSupport, parse3)
{
    const complex_double expected = CMPLX (0.0, 2.5);
    complex_double actual = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&actual, "2.5i");

    TEST_ASSERT (ok);
    TEST_ASSERT (complex_equals (expected, actual));
}

TEST (ComplexSupport, parse4)
{
    const complex_double expected = CMPLX (1.3, -2.5);
    complex_double actual = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&actual, "1.3-2.5i");

    TEST_ASSERT (ok);
    TEST_ASSERT (complex_equals (expected, actual));
}

TEST (ComplexSupport, parse5)
{
    const complex_double expected = CMPLX (1.0, 0.0);
    complex_double actual = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&actual, "1");

    TEST_ASSERT (ok);
    TEST_ASSERT (complex_equals (expected, actual));
}

TEST (ComplexSupport, parse6)
{
    complex_double a = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&a, "1.3-2.5");

    TEST_ASSERT_FALSE (ok);
}

TEST (ComplexSupport, parse7)
{
    complex_double a = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&a, "1.3i-2.5");

    TEST_ASSERT_FALSE (ok);
}

TEST (ComplexSupport, parse8)
{
    complex_double a = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&a, "1.3ij");

    TEST_ASSERT_FALSE (ok);
}

TEST (ComplexSupport, parse9)
{
    complex_double a = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&a, "1.3+3.5546ij");

    TEST_ASSERT_FALSE (ok);
}

TEST (ComplexSupport, parse10)
{
    complex_double a = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&a, "1.3ji");

    TEST_ASSERT_FALSE (ok);
}

TEST (ComplexSupport, parse11)
{
    complex_double a = CMPLX (0.0, 0.0);
    const bool ok = complex_parse (&a, "1.3+3.5546ji");

    TEST_ASSERT_FALSE (ok);
}
