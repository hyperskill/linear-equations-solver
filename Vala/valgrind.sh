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

make clean && VALGRIND=1 make && G_DEBUG=resident-modules G_DEBUG=gc-friendly G_SLICE=always-malloc valgrind --log-file=valgrind.txt --leak-resolution=high --num-callers=20 --leak-check=full bin/test_vala


