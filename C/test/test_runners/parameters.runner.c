// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include <unity/unity_fixture.h>

TEST_GROUP_RUNNER (Parameters)
{
    RUN_TEST_CASE (Parameters, default_parameters);
    RUN_TEST_CASE (Parameters, in);
    RUN_TEST_CASE (Parameters, out);
    RUN_TEST_CASE (Parameters, verbose);
    RUN_TEST_CASE (Parameters, new_stack);
}
