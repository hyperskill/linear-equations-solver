CCACHE = ccache
CC = $(CCACHE) clang
CXX = $(CCACHE) clang++
CPPFLAGS = -MT $@ -MMD -MP
TARGET_ARCH ?=

WARNINGS = -Weverything -Wno-used-but-marked-unused -Wno-c++98-compat -Wno-c++98-compat-pedantic -Wno-padded -Wno-vla -Wno-tautological-pointer-compare -Wno-covered-switch-default -Werror
WARNINGS_C_ONLY = -fno-common -Wno-complex-component-init
WARNINGS_FORCE_CXX = -Wno-deprecated -Wno-old-style-cast
WARNINGS_TEST = -Wno-switch-enum
WARNINGS_TEST_CXX = -Wno-zero-as-null-pointer-constant -Wno-float-equal -Wno-disabled-macro-expansion

INCLUDE += -I"include" -isystem"3rd" -isystem"/usr/local/include/"
LTO =
SECURITY = 
DEFINES =
DEBUG = -g3
OPENMP = -fopenmp
OPTIMIZE = -O3 -fstrict-aliasing -funsafe-math-optimizations
CXXSTANDARD = -std=gnu++1z
CSTANDARD = -std=c11

SANITIZERS = -fsanitize=address -fsanitize=undefined -fsanitize=leak -fno-omit-frame-pointer
THREAD_SANITIZER = -fsanitize=thread -fno-omit-frame-pointer

LD_LTO =
LD_INCLUDE = -L"/usr/local/lib"
LD_SECURITY =
LD_SYSTEM =

LIBS_OPENMP = -fopenmp=libiomp5
LIBS_SYSTEM = -lm
LIBS_THREAD = -pthread
LIBS_3RD =

FLAGS = $(OPTIMIZE) $(WARNINGS) $(INCLUDE) $(LTO) $(DEFINES) $(OPENMP)         \
    $(SECURITY) $(DEBUG)

CFLAGS = $(CSTANDARD) $(FLAGS) $(WARNINGS_C_ONLY)
CXXFLAGS = $(CXXSTANDARD) $(FLAGS) $(WARNINGS_FORCE_CXX)
LDFLAGS = $(LD_INCLUDE) $(LD_SECURITY) $(LD_LTO) $(LD_SYSTEM) $(DEBUG)
LIBS = $(LIBS_3RD) $(LIBS_OPENMP) $(LIBS_SYSTEM) $(LIBS_THREAD)
