OS?=
ifeq ($(OS),Windows_NT)
    PLATFORM := $(shell uname 2>NUL; false)
    ifeq ($(findstring MINGW,$(PLATFORM)),MINGW)
        DEL_NUL := $(shell rm -f NUL; false)
        ifeq ($(findstring MINGW64,$(PLATFORM)),MINGW64)
            BITS = "64"
        else
            BITS = "32"
        endif
    else
        PLATFORM = "Windows"
    endif	
else
    PLATFORM = $(shell uname)
endif

OS_SUPPORTED = false

ifeq ($(findstring NetBSD, $(PLATFORM)), NetBSD)
    include mk/netbsd_gcc.mk
    OS_SUPPORTED = true
endif

ifeq ($(findstring OpenBSD, $(PLATFORM)), OpenBSD)
    include mk/openbsd_clang.mk
    OS_SUPPORTED = true
endif

ifeq ($(findstring SunOS, $(PLATFORM)), SunOS)
    include mk/openindiana_gcc.mk
    OS_SUPPORTED = true
endif

ifeq ($(findstring FreeBSD, $(PLATFORM)), FreeBSD)
    include mk/freebsd_clang.mk
    OS_SUPPORTED = true
endif

ifeq ($(findstring Linux, $(PLATFORM)), Linux)
    include mk/linux_gcc.mk
    OS_SUPPORTED = true
endif

ifeq ($(findstring MINGW, $(PLATFORM)), MINGW)
    include mk/mingw_clang.mk
    EXEEXT = .exe
    OS_SUPPORTED = true
endif

ifeq ($(findstring Windows, $(PLATFORM)),Windows)
    $(error "install msys2: http://www.msys2.org/")
endif

ifeq ($(OS_SUPPORTED),false)
    $(error "OS is not supported")
endif

