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

LTO = -flto-partition=none -flto -ffat-lto-objects
INCLUDE = -I"include" -isystem"3rd"
SECURITY = -fPIC -fstack-protector-all --param ssp-buffer-size=4 -fstack-check\
    -mindirect-branch=thunk -fPIE -Wa,--noexecstack 
DEFINES = -D_FORTIFY_SOURCE=2
DEBUG = -g3
OPENMP = -fopenmp
OPTIMIZE = -O3 -fstrict-aliasing -funsafe-math-optimizations -ftracer
CXXSTANDARD = -std=c++1z

SANITIZERS = -fsanitize=address -fsanitize=undefined -fsanitize=leak -fno-omit-frame-pointer
THREAD_SANITIZER = -fsanitize=thread -fno-omit-frame-pointer

LD_LTO = -flto-partition=none -flto -ffat-lto-objects
LD_INCLUDE =
LD_SECURITY = -pie -Wl,-z,relro,-z,now -Wl,-z,noexecstack
LD_SYSTEM = -Wl,-O1 -rdynamic

LIBS_OPENMP = -fopenmp
LIBS_SYSTEM = -lm
LIBS_THREAD =  -pthread -Wl,--no-as-needed -lpthread
LIBS_3RD_CXX =
LIBS_TEST_CXX = -lboost_unit_test_framework

FLAGS = $(OPTIMIZE) $(WARNINGS) $(INCLUDE) $(LTO) $(DEFINES) $(OPENMP)         \
    $(SECURITY) $(DEBUG)
CXXFLAGS = $(CXXSTANDARD) $(FLAGS)
LDFLAGS = $(LD_INCLUDE) $(LD_SECURITY) $(LD_LTO) $(LD_SYSTEM) $(DEBUG)
LIBS = $(LIBS_3RD) $(LIBS_OPENMP) $(LIBS_SYSTEM) $(LIBS_THREAD)

