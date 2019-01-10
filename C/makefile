include mk/init.mk

OBJ_TEST += src/solver.o src/complex_support.o src/parameters.o src/util.o \
test/main.o test/solver.test.o test/parameters.test.o test/complex_support.test.o \
test/test_runners/parameters.runner.o test/test_runners/solver.runner.o test/test_runners/complex_support.runner.o

all: bin/test_c$(EXEEXT)

test: bin/test_c$(EXEEXT)

test_cpp: bin/test_cpp$(EXEEXT)

clean: clean_details

include mk/targets.mk

