#include "util.hpp"

#include <cassert>
#include <cstring>

using std::size_t;
using std::strcmp;
using std::strlen;

bool  NONNULL (1, 2) str_equal (const char* s1, const char* s2) noexcept
{
    assert (s1 != nullptr);
    assert (s2 != nullptr);

    const size_t s1_length = strlen (s1);
    const size_t s2_length = strlen (s2);
    if (s1_length == s2_length)
    {
        return strcmp (s1, s2) == 0;
    }

    return false;
}
