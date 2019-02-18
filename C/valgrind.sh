OS="`uname`"
case $OS in
  'Linux')
    alias make='make'
    ;;
  'FreeBSD')
    alias make='gmake'
    ;;
  *) ;;
esac

make clean && VALGRIND=1 make && valgrind --track-origins=yes --log-file=valgrind.txt --read-var-info=yes --leak-check=full bin/test_c


