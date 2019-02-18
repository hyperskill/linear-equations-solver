CCACHE = ccache
CC = $(CCACHE) gcc
CPPFLAGS = -MT $@ -MMD -MP
TARGET_ARCH ?=

WARNINGS = -w
LTO =
SECURITY =
DEFINES =
DEBUG = -g3
OPENMP = -fopenmp
OPTIMIZE = -O3 -fstrict-aliasing -funsafe-math-optimizations -ftracer
CSTANDARD = -std=c89

SANITIZERS =
THREAD_SANITIZER =

LD_LTO =
LD_INCLUDE =
LD_SECURITY = -Wl,--dynamicbase -Wl,--nxcompat
ifeq ($(findstring 64,$(BITS)),64)
	LD_SECURITY += -Wl,--high-entropy-va
endif
LD_SYSTEM = -Wl,-subsystem,console -Wl,-O1

LIBS_OPENMP = -fopenmp
LIBS_SYSTEM = -lm
LIBS_THREAD =  -pthread -Wl,--no-as-needed -lpthread

FLAGS = $(OPTIMIZE) $(LTO) $(DEFINES) $(OPENMP) $(SECURITY) $(DEBUG)
CFLAGS = $(CSTANDARD) $(FLAGS) $(WARNINGS)
LDFLAGS = $(LD_INCLUDE) $(LD_SECURITY) $(LD_LTO) $(LD_SYSTEM) $(DEBUG)
LIBS = $(LIBS_OPENMP) $(LIBS_SYSTEM) $(LIBS_THREAD)
