H_FILES_NUMBER=`find include -type f -name "*.h" -printf x 2> /dev/null | wc -c`

C_FILES_NUMBER=`find src -type f -name "*.c" -printf x 2> /dev/null | wc -c`
TEST_C_FILES_NUMBER=`find test -type f -name "*.c" -printf x 2> /dev/null | wc -c`

ASTYLE="astyle --style=allman --pad-oper --pad-first-paren-out --align-pointer=type --align-reference=type --lineend=windows --max-code-length=80 --indent-namespaces --convert-tabs"

if [ $C_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "src/*.c"
fi

if [ $H_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "include/*.h"
fi

if [ $TEST_C_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "test/*.c"
fi
