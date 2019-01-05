CCACHE = ccache
CC = $(CCACHE) clang
CXX = $(CCACHE) clang++
CPPFLAGS = -MT $@ -MMD -MP
TARGET_ARCH ?=

WARNINGS = -Weverything -Wno-padded -Wno-tautological-pointer-compare -Wno-covered-switch-default -Werror
WARNINGS_CXX_ONLY = -Wno-c++98-compat -Wno-c++98-compat-pedantic -Wno-shadow-field-in-constructor
WARNINGS_TESTS = -Wno-disabled-macro-expansion -Wno-global-constructors

INCLUDE = -I"include" -isystem"3rd"
LTO =
SECURITY = 
DEFINES = 
DEBUG = -g3
OPENMP = -fopenmp
OPTIMIZE = -O3 -fstrict-aliasing -funsafe-math-optimizations
CXXSTANDARD = -std=c++1z

SANITIZERS = 
THREAD_SANITIZER =

LD_LTO =
LD_INCLUDE = 
LD_SECURITY =
LD_SYSTEM =

LIBS_OPENMP = -fopenmp=libiomp5
LIBS_SYSTEM_CXX =
LIBS_THREAD =
LIBS_3RD_CXX =
LIBS_TEST_CXX = -lboost_unit_test_framework-mt

FLAGS = $(OPTIMIZE) $(WARNINGS) $(INCLUDE) $(LTO) $(DEFINES) $(OPENMP)         \
    $(SECURITY) $(DEBUG)

CXXFLAGS = $(CXXSTANDARD) $(FLAGS) $(WARNINGS_CXX_ONLY)
LDFLAGS_CXX = $(LDFLAGS_C)
LIBS_CXX = $(LIBS_3RD_CXX) $(LIBS_OPENMP) $(LIBS_SYSTEM_CXX) $(LIBS_THREAD)
