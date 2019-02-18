H_FILES_NUMBER=`find include -type f -name "*.h" -printf x 2> /dev/null | wc -c`
HPP_FILES_NUMBER=`find include -type f -name "*.hpp" -printf x 2> /dev/null | wc -c`

CPP_FILES_NUMBER=`find src -type f -name "*.cpp" -printf x 2> /dev/null | wc -c`
TEST_CPP_FILES_NUMBER=`find test -type f -name "*.cpp" -printf x 2> /dev/null | wc -c`

ASTYLE="astyle --style=allman --pad-oper --pad-first-paren-out --align-pointer=type --align-reference=type --lineend=windows --max-code-length=100 --indent-namespaces --convert-tabs"

if [ $CPP_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "src/*.cpp"
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
