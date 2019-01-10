CCACHE = ccache
CC = $(CCACHE) clang
CXX = $(CCACHE) clang++
CPPFLAGS = -MT $@ -MMD -MP
TARGET_ARCH ?=

WARNINGS = -Weverything -Wno-used-but-marked-unused -Wno-c++98-compat -Wno-c++98-compat-pedantic -Wno-padded -Wno-vla -Wno-tautological-pointer-compare -Wno-covered-switch-default -Werror
WARNINGS_C_ONLY = -Wbad-function-cast -Winit-self -fno-common
WARNINGS_FORCE_CXX = -Wno-deprecated -Wno-old-style-cast

INCLUDE = -I"include" -isystem"3rd"
LTO =
SECURITY = 
DEFINES =
DEBUG = -g3
OPENMP = -fopenmp
OPTIMIZE = -O3 -fstrict-aliasing -funsafe-math-optimizations
CXXSTANDARD = -std=gnu++1z
CSTANDARD = -std=gnu11

SANITIZERS = 
THREAD_SANITIZER =

LD_LTO =
LD_INCLUDE = 
LD_SECURITY =
LD_SYSTEM =

LIBS_OPENMP = -fopenmp=libiomp5
LIBS_SYSTEM =
LIBS_THREAD =
LIBS_3RD =

FLAGS = $(OPTIMIZE) $(WARNINGS) $(INCLUDE) $(LTO) $(DEFINES) $(OPENMP)         \
    $(SECURITY) $(DEBUG)

CFLAGS = $(CSTANDARD) $(FLAGS) $(WARNINGS_C_ONLY)
CXXFLAGS = $(CXXSTANDARD) $(FLAGS) $(WARNINGS_FORCE_CXX)
LDFLAGS = $(LD_INCLUDE) $(LD_SECURITY) $(LD_LTO) $(LD_SYSTEM) $(DEBUG)
LIBS = $(LIBS_3RD) $(LIBS_OPENMP) $(LIBS_SYSTEM) $(LIBS_THREAD)
