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


clean_details:
	find .  -name "*.o" -type f -delete
	find .  -name "*.d" -type f -delete
	find .  -name "*.orig" -type f -delete
	rm -f "bin/test_c$(EXEEXT)"
	rm -f "bin/test_cpp$(EXEEXT)"

help_details:
	@echo "The following are some of the valid targets for this makefile:"
	@echo "    all (the default if no target is provided)"
	@echo "    force_cpp"
	@echo "    clean"
	@echo "    help"

.PHONY: all help help_details clean clean_details test_cpp test

include $(wildcard $(patsubst %, %.d,$(basename $(OBJS))))
