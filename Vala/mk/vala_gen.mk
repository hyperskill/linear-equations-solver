$(SRC_C_TEST_VALA) : $(SRC_TEST_VALA)
	@rm -f $(SRC_C_TEST_VALA)
	@$(VALAC) $^ $(VALA_FLAGS)

src/main.c : $(SRC_VALA)
	@rm -f $(SRC_C_VALA)
	@$(VALAC) $^ $(VALA_FLAGS)
