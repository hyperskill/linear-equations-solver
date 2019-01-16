CCACHE = ccache
CC = $(CCACHE) i386-pc-msdosdjgpp-gcc
CXX = $(CCACHE) i386-pc-msdosdjgpp-g++
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
    -Wsubobject-linkage -Wsuggest-attribute=pure -Wsuggest-attribute=const    \
    -Wsuggest-attribute=noreturn -Wmissing-noreturn -Wmissing-format-attribute\
    -Wsuggest-attribute=malloc -Wsuggest-attribute=format                     \
    -Wsuggest-attribute=cold                                                  \
    -Werror
WARNINGS_TESTS = 

LTO = #-flto-partition=none -flto -ffat-lto-objects
INCLUDE += -I"include" -isystem"3rd"
SECURITY =
DEFINES = -D_FORTIFY_SOURCE=2 -D__STDC_FORMAT_MACROS -DFORCE_STATIC_BOOST
DEBUG = -g3
OPENMP =
OPTIMIZE = -O3 -fstrict-aliasing -funsafe-math-optimizations -ftracer
CXXSTANDARD = -std=gnu++1z
CSTANDARD = -std=gnu11

SANITIZERS =
THREAD_SANITIZER =

LD_LTO = #-flto-partition=none -flto -ffat-lto-objects
LD_INCLUDE =
LD_SECURITY =
LD_SYSTEM =

LIBS_OPENMP =
LIBS_SYSTEM = -lm
LIBS_THREAD =
LIBS_3RD =
LIBS_TEST = 

FLAGS = $(OPTIMIZE) $(WARNINGS) $(INCLUDE) $(LTO) $(DEFINES) $(OPENMP)         \
    $(SECURITY) $(DEBUG)
CXXFLAGS = $(CXXSTANDARD) $(FLAGS)
LDFLAGS = $(LD_INCLUDE) $(LD_SECURITY) $(LD_LTO) $(LD_SYSTEM) $(DEBUG)
LIBS = $(LIBS_3RD) $(LIBS_OPENMP) $(LIBS_SYSTEM) $(LIBS_THREAD)

