VALGRIND ?= 0
EXEEXT = 

OBJ_PLATFORM_SPECIFIC_CXX =

OBJ_SOLUTION_CXX = $(OBJ_PLATFORM_SPECIFIC_CXX)

OBJ_TEST_CXX =

OBJS = $(OBJ_TEST_CXX) $(OBJ_SOLUTION_CXX)

include mk/detect_os.mk
include mk/optional_ccache.mk
include mk/disable_sanitizers_if_enabled_valgrind.mk

COMPILE.cpp = $(CXX) $(CXXFLAGS) $(CPPFLAGS) $(TARGET_ARCH) -c

%.o : %.cpp
%.o : %.cpp %.d
	$(COMPILE.cpp) $(OUTPUT_OPTION) $<

%.d: ;
.PRECIOUS: %.d

