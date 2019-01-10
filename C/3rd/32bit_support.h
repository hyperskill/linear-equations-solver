#ifndef _32BIT_SUPPORT_H
#define _32BIT_SUPPORT_H

#pragma once

#include <limits.h>
#include <inttypes.h>

#if ((SIZE_MAX) == (UINT32_MAX))
#define CAST_UINT32_TO_SIZE_T
#define CAST_SIZE_T_TO_UINT32
#else
#define CAST_UINT32_TO_SIZE_T (size_t)
#define CAST_SIZE_T_TO_UINT32 (uint32_t)
#endif

#endif
