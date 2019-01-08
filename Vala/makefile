include mk/init.mk

SRC_VALA = src/main.vala src/error.vala src/util.vala src/complex.vala src/parameters.vala src/solver.vala src/number_solutions.vala

SRC_TEST_VALA += src/error.vala src/util.vala src/complex.vala src/parameters.vala src/solver.vala src/number_solutions.vala \
    test/test_util.vala test/parameters.test.vala test/complex.test.vala test/util.test.vala test/solver.test.vala test/main.vala


all: test

release: bin/solution_vala$(EXEEXT)

test: bin/test_vala$(EXEEXT)

thread_test: bin/thread_test_vala$(EXEEXT)

clean: clean_details

help: help_details

include mk/targets.mk

