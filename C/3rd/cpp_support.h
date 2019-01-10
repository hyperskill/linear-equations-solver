#ifndef CPP_SUPPORT_H
#define CPP_SUPPORT_H

#pragma once

#include <inttypes.h>

#ifndef __cplusplus
#include <stdalign.h>
#include <stdbool.h>
#define noexcept
#define nullptr ((void*) 0)
#define constexpr
#ifndef thread_local
#define thread_local _Thread_local
#define STATIC_CONST static const
#endif
#else
#define STATIC_CONST constexpr
#ifdef _WIN32
#include <malloc.h>
#else
#include <alloca.h>
#endif
#endif


#ifdef  __cplusplus
#define BEGIN_DECLS  extern "C" {
#define END_DECLS    }
#else
#define BEGIN_DECLS
#define END_DECLS
#endif

#endif