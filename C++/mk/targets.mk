./bin/solution_cpp$(EXEEXT): $(OBJ_SOLUTION_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS_CXX) $(LIBS_CXX) $(LIBS_3RD_CXX)

./bin/debug_cpp$(EXEEXT): CXXFLAGS += $(SANITIZERS)
./bin/debug_cpp$(EXEEXT): CFLAGS += $(SANITIZERS)
./bin/debug_cpp$(EXEEXT): $(OBJ_SOLUTION_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS_CXX) $(LIBS_CXX) $(LIBS_3RD_CXX) $(SANITIZERS)

./bin/thread_debug_cpp$(EXEEXT): CXXFLAGS += $(THREAD_SANITIZER)
./bin/thread_debug_cpp$(EXEEXT): CFLAGS += $(THREAD_SANITIZER)
./bin/thread_debug_cpp$(EXEEXT): $(OBJ_SOLUTION_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS_CXX) $(LIBS_CXX) $(LIBS_3RD_CXX) $(THREAD_SANITIZER)

./bin/test_cpp$(EXEEXT): CXXFLAGS +=  $(DEBUG_STL) $(SANITIZERS) $(WARNINGS_TESTS)
./bin/test_cpp$(EXEEXT): $(OBJ_TEST_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS_CXX) $(LIBS_CXX) $(LIBS_TEST_CXX) $(SANITIZERS)

./bin/thread_test_cpp$(EXEEXT): CXXFLAGS +=  $(DEBUG_STL) $(THREAD_SANITIZER)
./bin/thread_test_cpp$(EXEEXT): CFLAGS += $(THREAD_SANITIZER)
./bin/thread_test_cpp$(EXEEXT): $(OBJ_TEST_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS_CXX) $(LIBS_CXX) $(LIBS_TEST_CXX) $(THREAD_SANITIZER)

clean_details:
	find .  -name "*.o"    ! -path "./.git/*" ! -path "./msvc2017/*" -type f -delete
	find .  -name "*.d"    ! -path "./.git/*" ! -path "./msvc2017/*" -type f -delete
	find .  -name "*.orig" ! -path "./.git/*" ! -path "./msvc2017/*" -type f -delete
	rm -f "bin/solution_cpp$(EXEEXT)"
	rm -f "bin/debug_cpp$(EXEEXT)"
	rm -f "bin/thread_debug_cpp$(EXEEXT)"
	rm -f "bin/test_cpp$(EXEEXT)"
	rm -f "bin/thread_test_cpp$(EXEEXT)"

help_details:
	@echo "The following are some of the valid targets for this makefile:"
	@echo "    all (the default if no target is provided)(=debug)"
	@echo "    force_cpp (building c solution with c++ compiler)"
	@echo "    release"
	@echo "    debug"
	@echo "    thread_debug (searching thread races with tsan)"
	@echo "    clean"
	@echo "    help"

.PHONY: all force_cpp release debug thread_debug help help_details clean clean_details

include $(wildcard $(patsubst %, %.d,$(basename $(OBJS))))
