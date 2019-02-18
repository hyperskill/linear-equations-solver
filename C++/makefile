include mk/init.mk

OBJ_SOLUTION_CXX += src/main.o src/number_solutions.o src/solver.o src/complex.o src/parameters.o src/util.o
OBJ_TEST_CXX += src/number_solutions.o src/solver.o src/complex.o src/parameters.o src/util.o test/main.o test/parameters.test.o test/complex.test.o test/solver.test.o

all: test

release: bin/solution_cpp$(EXEEXT)

test: bin/test_cpp$(EXEEXT)

thread_test: bin/thread_test_cpp$(EXEEXT)

clean: clean_details

help: help_details

include mk/targets.mk

