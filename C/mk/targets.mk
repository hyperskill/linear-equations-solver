./bin/solution_c$(EXEEXT):  $(OBJ_SOLUTION)
	$(CC) -o $@ $^ $(LDFLAGS) $(LIBS)

./bin/solution_cpp$(EXEEXT): CFLAGS = $(CXXFLAGS)
./bin/solution_cpp$(EXEEXT): CC = $(CXX)
./bin/solution_cpp$(EXEEXT):  $(OBJ_SOLUTION)
	$(CC) -o $@ $^ $(LDFLAGS) $(LIBS)

./bin/test_c$(EXEEXT): CFLAGS += $(SANITIZERS) $(WARNINGS_TEST)
./bin/test_c$(EXEEXT): $(OBJ_TEST)
	$(CC) -o $@ $^ $(LDFLAGS) $(LIBS) $(SANITIZERS)

./bin/test_cpp$(EXEEXT): CFLAGS = $(CXXFLAGS)
./bin/test_cpp$(EXEEXT): CC = $(CXX)
./bin/test_cpp$(EXEEXT): CFLAGS += $(SANITIZERS) $(WARNINGS_TEST) $(WARNINGS_TEST_CXX)
./bin/test_cpp$(EXEEXT): $(OBJ_TEST)
	$(CC) -o $@ $^ $(LDFLAGS) $(LIBS) $(SANITIZERS)
	
./bin/thread_test_c$(EXEEXT): CFLAGS += $(THREAD_SANITIZER) $(WARNINGS_TEST)
./bin/thread_test_c$(EXEEXT): $(OBJ_TEST)
	$(CC) -o $@ $^ $(LDFLAGS) $(LIBS) $(THREAD_SANITIZER)

./bin/thread_test_cpp$(EXEEXT): CFLAGS = $(CXXFLAGS)
./bin/thread_test_cpp$(EXEEXT): CC = $(CXX)
./bin/thread_test_cpp$(EXEEXT): CFLAGS += $(THREAD_SANITIZER) $(WARNINGS_TEST) $(WARNINGS_TEST_CXX)
./bin/thread_test_cpp$(EXEEXT): $(OBJ_TEST)
	$(CC) -o $@ $^ $(LDFLAGS) $(LIBS) $(THREAD_SANITIZER)


clean_details:
	find .  -name "*.o" -type f -delete
	find .  -name "*.d" -type f -delete
	find .  -name "*.orig" -type f -delete
	rm -f "bin/solution_c$(EXEEXT)"
	rm -f "bin/solution_cpp$(EXEEXT)"
	rm -f "bin/test_c$(EXEEXT)"
	rm -f "bin/test_cpp$(EXEEXT)"
	rm -f "bin/thread_test_c$(EXEEXT)"
	rm -f "bin/thread_test_cpp$(EXEEXT)"

help_details:
	@echo "The following are some of the valid targets for this makefile:"
	@echo "    all (the default if no target is provided=test)"
	@echo "    release"
	@echo "    release_cpp"
	@echo "    test"
	@echo "    test_cpp"
	@echo "    thread_test"
	@echo "    thread_test_cpp"
	@echo "    clean"
	@echo "    help"

.PHONY: all help help_details clean clean_details test_cpp test

include $(wildcard $(patsubst %, %.d,$(basename $(OBJS))))
