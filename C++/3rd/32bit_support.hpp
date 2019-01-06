#ifndef _32BIT_SUPPORT_H
#define _32BIT_SUPPORT_H

#pragma once

#include <climits>
#include <cinttypes>

#if ((SIZE_MAX) == (UINT32_MAX))
#define CAST_UINT32_TO_SIZE_T 
#else
#define CAST_UINT32_TO_SIZE_T static_cast<size_t>
#endif

#endif
