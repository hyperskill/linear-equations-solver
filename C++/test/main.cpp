// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#ifndef BOOST_TEST_MAIN
#define BOOST_TEST_MAIN
#undef  BOOST_TEST_NO_MAIN
#ifdef FORCE_STATIC_BOOST
#include <boost/test/included/unit_test.hpp>
#else
#define BOOST_TEST_DYN_LINK
#include <boost/test/unit_test.hpp>
#endif
#endif
