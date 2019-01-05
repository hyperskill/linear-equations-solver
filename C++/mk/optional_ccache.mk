CCACHE_VER := $(shell $(CCACHE) -V 2>/dev/null; false)
ifeq ($(CCACHE_VER),)
	CCACHE =
endif
