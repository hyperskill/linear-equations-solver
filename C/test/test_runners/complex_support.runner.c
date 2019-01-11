// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include <unity_fixture.h>

TEST_GROUP_RUNNER (ComplexSupport)
{
    RUN_TEST_CASE (ComplexSupport, to_string1);
    RUN_TEST_CASE (ComplexSupport, to_string2);
    RUN_TEST_CASE (ComplexSupport, to_string3);
    RUN_TEST_CASE (ComplexSupport, to_string4);
    RUN_TEST_CASE (ComplexSupport, to_string5);
    RUN_TEST_CASE (ComplexSupport, equals1);
    RUN_TEST_CASE (ComplexSupport, equals2);
    RUN_TEST_CASE (ComplexSupport, equals3);
    RUN_TEST_CASE (ComplexSupport, parse1);
    RUN_TEST_CASE (ComplexSupport, parse2);
    RUN_TEST_CASE (ComplexSupport, parse3);
    RUN_TEST_CASE (ComplexSupport, parse4);
    RUN_TEST_CASE (ComplexSupport, parse5);
    RUN_TEST_CASE (ComplexSupport, parse6);
    RUN_TEST_CASE (ComplexSupport, parse7);
    RUN_TEST_CASE (ComplexSupport, parse8);
    RUN_TEST_CASE (ComplexSupport, parse9);
    RUN_TEST_CASE (ComplexSupport, parse10);
    RUN_TEST_CASE (ComplexSupport, parse11);
}
