CPP_FILES_NUMBER=`find src -type f -name "*.cpp" -printf x 2> /dev/null | wc -c`
C_FILES_NUMBER=`find src -type f -name "*.c" -printf x 2> /dev/null | wc -c`
H_FILES_NUMBER=`find include -type f -name "*.h" -printf x 2> /dev/null | wc -c`
HPP_FILES_NUMBER=`find include -type f -name "*.hpp" -printf x 2> /dev/null | wc -c`

TEST_CPP_FILES_NUMBER=`find test -type f -name "*.cpp" -printf x 2> /dev/null | wc -c`
TEST_HPP_FILES_NUMBER=`find test -type f -name "*.hpp" -printf x 2> /dev/null | wc -c`
TEST_C_FILES_NUMBER=`find test -type f -name "*.c" -printf x 2> /dev/null | wc -c`
TEST_H_FILES_NUMBER=`find test -type f -name "*.h" -printf x 2> /dev/null | wc -c`

VALA_FILES_NUMBER=`find src -type f -name "*.vala" -printf x 2> /dev/null | wc -c`
VAPI_FILES_NUMBER=`find vapi -type f -name "*.vapi" -printf x 2> /dev/null | wc -c`

BENCH_CPP_FILES_NUMBER=`find bench -type f -name "*.cpp" -printf x 2> /dev/null | wc -c`
BENCH_HPP_FILES_NUMBER=`find bench -type f -name "*.hpp" -printf x 2> /dev/null | wc -c`

ASTYLE="astyle --style=allman --pad-oper --pad-first-paren-out --align-pointer=type --align-reference=type --lineend=windows --max-code-length=100 --indent-namespaces --convert-tabs"

if [ $CPP_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "src/*.cpp"
fi

if [ $C_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "src/*.c"
fi

if [ $H_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "include/*.h"
fi

if [ $HPP_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "include/*.hpp"
fi

if [ $TEST_CPP_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "test/*.cpp"
fi

if [ $TEST_HPP_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "test/*.hpp"
fi

if [ $TEST_C_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "test/*.c"
fi

if [ $TEST_H_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "test/*.h"
fi

if [ $VALA_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "src/*.vala"
fi

if [ $VAPI_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "vapi/*.vapi"
fi

if [ $BENCH_CPP_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "bench/*.cpp"
fi

if [ $BENCH_HPP_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "bench/*.hpp"
fi
