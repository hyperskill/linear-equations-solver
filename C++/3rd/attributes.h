#ifndef ATTRIBUTES_H
#define ATTRIBUTES_H

#pragma once

#ifdef __GNUC__
#define NONNULL(...) __attribute__((nonnull (__VA_ARGS__)))
#else
#define NONNULL(...) 
#endif

#endif 