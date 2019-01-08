VALA_FILES_NUMBER=`find src -type f -name "*.vala" -printf x 2> /dev/null | wc -c`
TEST_VALA_FILES_NUMBER=`find test -type f -name "*.vala" -printf x 2> /dev/null | wc -c`

RCFILE=.astylerc
ASTYLE="astyle --options=$RCFILE"

if [ $VALA_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "src/*.vala"
fi

if [ $TEST_VALA_FILES_NUMBER -gt 0 ]; then
    $ASTYLE --recursive "test/*.vala"
fi
