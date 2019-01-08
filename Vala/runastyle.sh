VALA_FILES_NUMBER=`find src -type f -name "*.vala" -printf x 2> /dev/null | wc -c`
TEST_VALA_FILES_NUMBER=`find test -type f -name "*.vala" -printf x 2> /dev/null | wc -c`

ASTYLE="astyle --style=allman --pad-oper --pad-first-paren-out --align-pointer=type --align-reference=type --lineend=windows --max-code-length=100 --indent-namespaces --convert-tabs"

if [ $VALA_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "src/*.vala"
fi

if [ $TEST_VALA_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "test/*.vala"
fi
