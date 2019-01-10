// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
#include "parameters.h"

#include <assert.h>
#include <stdlib.h>

#include "util.h"

struct parameters
{
    const char* in;
    const char* out;
    bool verbose;
    bool stack;
};

const size_t parameters_size = sizeof (parameters);

parameters* (parameters_new) (size_t argc, const char* const* argv,
                              void* memory) noexcept
{
    assert (argv != nullptr);

    parameters* result = (parameters*)memory;
    if (memory == nullptr)
    {
        result  = (parameters*)malloc (sizeof (parameters));
        if (result == nullptr)
        {
            return nullptr;
        }
        result->stack = false;
    }
    else
    {
        result->stack = true;
    }
    result->in = "input.txt";
    result->out = "output.txt";
    result->verbose = false;
    bool need_assigned_in = true;
    bool need_assigned_out = true;
    for (size_t i = 0; i < argc; ++i)
    {
        assert (argv[i] != nullptr);
        if (need_assigned_in && str_equal (argv[i], "-in"))
        {
            if (i < (argc - 1))
            {
                result->in = argv[i + 1];
                need_assigned_in = false;
                ++i;
            }
        }
        else if (need_assigned_out && str_equal (argv[i], "-out"))
        {
            if (i < argc - 1)
            {
                result->out = argv[i + 1];
                need_assigned_out = false;
                ++i;
            }
        }
        else if (!result->verbose && str_equal (argv[i], "-verbose"))
        {
            result->verbose = true;
        }
    }
    return result;
}
void parameters_free (parameters* p)
{
    if (p != nullptr)
    {
        if (!p->stack)
        {
            free (p);
        }
    }

}
const char* parameters_get_in (const parameters* p)
{
    assert (p != nullptr);
    return p->in;
}
const char* parameters_get_out (const parameters* p)
{
    assert (p != nullptr);
    return p->out;
}
bool parameters_get_verbose (const parameters* p)
{
    assert (p != nullptr);
    return p->verbose;
}
