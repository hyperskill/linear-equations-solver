VALGRIND ?= 0
EXEEXT = 

OBJ_UNITY = 3rd/unity/unity.o 3rd/unity/unity_fixture.o

OBJ_TEST = $(OBJ_UNITY)

OBJ_SOLUTION =

OBJS = $(OBJ_TEST) $(OBJ_SOLUTION)

include mk/detect_os.mk
include mk/optional_ccache.mk
include mk/disable_sanitizers_if_enabled_valgrind.mk

COMPILE.c = $(CC) $(CFLAGS) $(CPPFLAGS) $(TARGET_ARCH) -c

%.o : %.c
%.o : %.c %.d
	$(COMPILE.c) $(OUTPUT_OPTION) $<

%.d: ;
.PRECIOUS: %.d

