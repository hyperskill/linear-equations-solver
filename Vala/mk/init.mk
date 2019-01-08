VALGRIND ?= 0
EXEEXT = 

SRC_C_VALA = $(SRC_VALA:.vala=.c)
OBJ_VALA = $(SRC_VALA:.vala=.o)

SRC_C_TEST_VALA = $(SRC_TEST_VALA:.vala=.c)
OBJ_TEST_VALA = $(SRC_TEST_VALA:.vala=.o)

OBJS = $(OBJ_TEST_VALA) $(OBJ_VALA)

include mk/detect_os.mk
include mk/optional_ccache.mk
include mk/disable_sanitizers_if_enabled_valgrind.mk

COMPILE.c = $(CC) $(CFLAGS) $(CPPFLAGS) $(TARGET_ARCH) -c

%.o : %.c
%.o : %.c %.d
	$(COMPILE.c) $(OUTPUT_OPTION) $<

%.d: ;
.PRECIOUS: %.d
