CCACHE = ccache
CC = $(CCACHE) gcc
CXX = $(CCACHE) g++
CPPFLAGS = -MT $@ -MMD -MP
TARGET_ARCH ?=

WARNINGS = -Wall -Wextra -pedantic -Wstrict-aliasing=2 -Wformat-security      \
    -Wstrict-overflow=5 -Wfloat-equal -Wformat-extra-args -Wshadow -Winit-self\
    -Wswitch-default -Wformat-nonliteral -Wdouble-promotion -Wnull-dereference\
    -Walloca -Wdouble-promotion -Wduplicated-branches -Wduplicated-cond -Wchkp\
    -Wconversion -Wlogical-not-parentheses -Walloc-zero -Wcast-qual -Wrestrict\
    -Wformat-y2k -Wcast-align -Wlogical-op -Wwrite-strings -Wsign-conversion  \
    -Wredundant-decls -Wmissing-include-dirs -Wswitch-enum -Wstack-protector  \
    -Wunused-const-variable=2 -Wdangling-else -Wnonnull -Wuseless-cast        \
    -Wold-style-cast -Woverloaded-virtual -Wsuggest-final-types -Weffc++      \
    -Wsuggest-final-methods -Wsuggest-override -Wzero-as-null-pointer-constant\
    -Wsubobject-linkage -Werror
WARNINGS_TESTS = 

LTO =
INCLUDE = -I"include" -isystem"3rd"
SECURITY = 
    
DEFINES = -D_FORTIFY_SOURCE=2
DEBUG = -g3
OPENMP = -fopenmp
OPTIMIZE = -O3 -fstrict-aliasing -funsafe-math-optimizations -ftracer
CXXSTANDARD = -std=c++1z

SANITIZERS = 
THREAD_SANITIZER = 

LD_LTO = 
LD_INCLUDE =
LD_SECURITY =
LD_SYSTEM =

LIBS_OPENMP = -fopenmp
LIBS_SYSTEM = -lm
LIBS_THREAD = -pthread
LIBS_3RD =
LIBS_TEST = -lboost_unit_test_framework

FLAGS = $(OPTIMIZE) $(WARNINGS) $(INCLUDE) $(LTO) $(DEFINES) $(OPENMP)         \
    $(SECURITY) $(DEBUG)
CXXFLAGS = $(CXXSTANDARD) $(FLAGS)
LDFLAGS = $(LD_INCLUDE) $(LD_SECURITY) $(LD_LTO) $(LD_SYSTEM) $(DEBUG)
LIBS = $(LIBS_3RD) $(LIBS_OPENMP) $(LIBS_SYSTEM) $(LIBS_THREAD)

