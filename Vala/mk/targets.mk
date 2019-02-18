include mk/vala_gen.mk

./bin/solution_vala$(EXEEXT) : CFLAGS += $(VALA_C_FLAGS)
./bin/solution_vala$(EXEEXT) : LIBS += $(VALA_C_LIBS)
./bin/solution_vala$(EXEEXT) : $(SRC_C_VALA) $(OBJ_VALA)
	$(CC) -o $@ $(OBJ_VALA) $(LDFLAGS) $(LIBS)

./bin/test_vala$(EXEEXT) : CFLAGS += $(SANITIZERS) $(VALA_C_FLAGS)
./bin/test_vala$(EXEEXT) : LIBS += $(VALA_C_LIBS)
./bin/test_vala$(EXEEXT) : $(SRC_C_TEST_VALA) $(OBJ_TEST_VALA)
	$(CC) -o $@ $(OBJ_TEST_VALA) $(LDFLAGS) $(LIBS) $(SANITIZERS) 

./bin/thread_test_vala$(EXEEXT) : CFLAGS += $(THREAD_SANITIZER) $(VALA_C_FLAGS)
./bin/thread_test_vala$(EXEEXT) : LIBS += $(VALA_C_LIBS)
./bin/thread_test_vala$(EXEEXT) : $(SRC_C_TEST_VALA) $(OBJ_TEST_VALA)
	$(CC) -o $@ $(OBJ_TEST_VALA) $(LDFLAGS) $(LIBS) $(THREAD_SANITIZER) 

clean_details:
	rm -f $(SRC_C_TEST_VALA)
	rm -f $(SRC_C_VALA)
	find .  -name "*.o"    ! -path "./.git/*" ! -path "./msvc_2017/*" -type f -delete
	find .  -name "*.d"    ! -path "./.git/*" ! -path "./msvc_2017/*" -type f -delete
	find .  -name "*.orig" ! -path "./.git/*" ! -path "./msvc_2017/*" -type f -delete
	rm -f "bin/test_vala$(EXEEXT)"
	rm -f "bin/thread_test_vala$(EXEEXT)"
	rm -f "bin/solution_vala$(EXEEXT)"

help_details:
	@echo "The following are some of the valid targets for this makefile:"
	@echo "    all (the default if no target is provided)(=test)"
	@echo "    release"
	@echo "    test (with msan)"
	@echo "    thread_test (searching thread races with tsan)"
	@echo "    clean"
	@echo "    help"

.PHONY: all help help_details clean clean_details

include $(wildcard $(patsubst %, %.d,$(basename $(OBJS))))
