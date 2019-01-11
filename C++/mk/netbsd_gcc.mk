CCACHE = ccache
CC = $(CCACHE) /usr/pkg/gcc8/bin/gcc
CXX = $(CCACHE) /usr/pkg/gcc8/bin/g++
CPPFLAGS = -MT $@ -MMD -MP
TARGET_ARCH ?=

WARNINGS = -Wall -Wextra -pedantic -Wstrict-aliasing=2 -Wformat-security      \
    -Wstrict-overflow=5 -Wfloat-equal -Wformat-extra-args -Wshadow -Winit-self\
    -Wswitch-default -Wformat-nonliteral -Wdouble-promotion -Wnull-dereference\
    -Walloca -Wdouble-promotion -Wduplicated-branches -Wduplicated-cond -Wchkp\
    -Wconversion -Wlogical-not-parentheses -Walloc-zero -Wcast-qual -Wrestrict\
    -Wformat-y2k -Wcast-align -Wlogical-op -Wwrite-strings -Wsign-conversion  \
    -Wredundant-decls -Wmissing-include-dirs -Wswitch-enum                    \
    -Wunused-const-variable=2 -Wdangling-else -Wnonnull -Wuseless-cast        \
    -Wold-style-cast -Woverloaded-virtual -Wsuggest-final-types -Weffc++      \
    -Wsuggest-final-methods -Wsuggest-override -Wzero-as-null-pointer-constant\
    -Wsubobject-linkage -Werror
WARNINGS_TESTS = 

LTO = -flto-partition=none -flto -ffat-lto-objects
INCLUDE = -I"include" -isystem"3rd" -isystem"/usr/pkg/include"
SECURITY =
DEFINES = -D_FORTIFY_SOURCE=2 -DFORCE_STATIC_BOOST
DEBUG = -g3
OPENMP = -fopenmp
OPTIMIZE = -O3 -fstrict-aliasing -funsafe-math-optimizations -ftracer
CXXSTANDARD = -std=c++1z

SANITIZERS =
THREAD_SANITIZER =

LD_LTO = -flto-partition=none -flto -ffat-lto-objects
LD_INCLUDE = -L"/usr/pkg/lib"
LD_SECURITY =
LD_SYSTEM =

LIBS_OPENMP = -fopenmp
LIBS_SYSTEM = -lm
LIBS_THREAD =  -pthread -Wl,--no-as-needed -lpthread
LIBS_3RD =
LIBS_TEST =

FLAGS = $(OPTIMIZE) $(WARNINGS) $(INCLUDE) $(LTO) $(DEFINES) $(OPENMP)         \
    $(SECURITY) $(DEBUG)
CXXFLAGS = $(CXXSTANDARD) $(FLAGS)
LDFLAGS = $(LD_INCLUDE) $(LD_SECURITY) $(LD_LTO) $(LD_SYSTEM) $(DEBUG)
LIBS = $(LIBS_3RD) $(LIBS_OPENMP) $(LIBS_SYSTEM) $(LIBS_THREAD)
