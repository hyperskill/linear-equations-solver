./bin/solution_cpp$(EXEEXT): $(OBJ_SOLUTION_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS) $(LIBS)

./bin/debug_cpp$(EXEEXT): CXXFLAGS += $(SANITIZERS)
./bin/debug_cpp$(EXEEXT): $(OBJ_SOLUTION_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS) $(LIBS) $(SANITIZERS)

./bin/thread_debug_cpp$(EXEEXT): CXXFLAGS += $(THREAD_SANITIZER)
./bin/thread_debug_cpp$(EXEEXT): $(OBJ_SOLUTION_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS) $(LIBS) $(THREAD_SANITIZER)

./bin/test_cpp$(EXEEXT): CXXFLAGS += $(SANITIZERS) $(WARNINGS_TESTS)
./bin/test_cpp$(EXEEXT): $(OBJ_TEST_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS) $(LIBS) $(LIBS_TEST) $(SANITIZERS)

./bin/thread_test_cpp$(EXEEXT): CXXFLAGS += $(THREAD_SANITIZER) $(WARNINGS_TESTS)
./bin/thread_test_cpp$(EXEEXT): $(OBJ_TEST_CXX)
	$(CXX) -o $@ $^ $(LDFLAGS) $(LIBS) $(LIBS_TEST) $(THREAD_SANITIZER)

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
	@echo "    all (the default if no target is provided)(=test)"
	@echo "    release"
	@echo "    test (with msan)"
	@echo "    thread_test (searching thread races with tsan)"
	@echo "    clean"
	@echo "    help"

.PHONY: all force_cpp release debug thread_debug help help_details clean clean_details

include $(wildcard $(patsubst %, %.d,$(basename $(OBJS))))
