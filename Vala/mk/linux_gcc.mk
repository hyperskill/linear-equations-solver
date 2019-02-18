CCACHE = ccache
CC = $(CCACHE) gcc
CPPFLAGS = -MT $@ -MMD -MP
TARGET_ARCH ?=

WARNINGS = -w
LTO = -flto-partition=none -flto -ffat-lto-objects
SECURITY = -fPIC -fstack-protector-all --param ssp-buffer-size=4 -fstack-check\
    -mindirect-branch=thunk -fPIE -Wa,--noexecstack 
DEFINES = -D_FORTIFY_SOURCE=2
DEBUG = -g3
OPENMP = -fopenmp
OPTIMIZE = -O3 -fstrict-aliasing -funsafe-math-optimizations -ftracer
CSTANDARD = -std=c89

SANITIZERS = -fsanitize=address -fsanitize=undefined -fsanitize=leak -fno-omit-frame-pointer
THREAD_SANITIZER = -fsanitize=thread -fno-omit-frame-pointer

LD_LTO = -flto-partition=none -flto -ffat-lto-objects
LD_INCLUDE =
LD_SECURITY = -pie -Wl,-z,relro,-z,now -Wl,-z,noexecstack
LD_SYSTEM = -Wl,-O1 -rdynamic

LIBS_OPENMP = -fopenmp
LIBS_SYSTEM = -lm
LIBS_THREAD =  -pthread -Wl,--no-as-needed -lpthread

FLAGS = $(OPTIMIZE) $(LTO) $(DEFINES) $(OPENMP) $(SECURITY) $(DEBUG)
CFLAGS = $(CSTANDARD) $(FLAGS) $(WARNINGS)
LDFLAGS = $(LD_INCLUDE) $(LD_SECURITY) $(LD_LTO) $(LD_SYSTEM) $(DEBUG)
LIBS = $(LIBS_OPENMP) $(LIBS_SYSTEM) $(LIBS_THREAD)

