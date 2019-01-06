// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include "parameters.hpp"

#include <cassert>
#include <cstring>

using std::size_t;
using std::strcmp;
using std::strlen;

static bool  NONNULL (1, 2) str_equal (const char* s1, const char* s2) noexcept
{
    const size_t s1_length = strlen (s1);
    const size_t s2_length = strlen (s2);
    if (s1_length == s2_length)
    {
        return strcmp (s1, s2) == 0;
    }

    return false;
}

parameters::parameters (size_t argc, const char* argv[]) noexcept
    : in ("input.txt")
    , out ("output.txt")
    , verbose (false)
{
    assert (argv != nullptr);

    bool need_assigned_in = true;
    bool need_assigned_out = true;
    for (size_t i = 0; i < argc; ++i)
    {
        assert (argv[i] != nullptr);
        if (need_assigned_in && str_equal (argv[i], "-in"))
        {
            if (i < (argc - 1))
            {
                in = argv[i + 1];
                need_assigned_in = false;
                ++i;
            }
        }
        else if (need_assigned_out && str_equal (argv[i], "-out"))
        {
            if (i < argc - 1)
            {
                out = argv[i + 1];
                need_assigned_out = false;
                ++i;
            }
        }
        else if (!verbose && str_equal (argv[i], "-verbose"))
        {
            verbose = true;
        }
    }
}

const char* parameters::get_in() const noexcept
{
    return in;
}

const char* parameters::get_out() const noexcept
{
    return out;
}

bool parameters::is_verbose() const noexcept
{
    return verbose;
}
