void parameters_tests ()
{
    Test.add_func ("/default_parameters_test", default_parameters_test);
    Test.add_func ("/in_parameters_test", in_parameters_test);
    Test.add_func ("/out_parameters_test", out_parameters_test);
    Test.add_func ("/verbose_parameters_test", verbose_parameters_test);
}

void default_parameters_test()
{
    Parameters p = new Parameters (new string[] {""});

    assert ("input.txt" == p.in);
    assert ("output.txt" == p.out);
    assert (false == p.verbose);
}

void in_parameters_test()
{
    Parameters p = new Parameters (new string[] {"-in", "in.txt"});

    assert ("in.txt" == p.in);
}

void out_parameters_test()
{
    Parameters p = new Parameters (new string[] {"-out", "out2.txt"});

    assert ("out2.txt" == p.out);
}

void verbose_parameters_test()
{
    Parameters p = new Parameters (new string[] {"-verbose"});

    assert (p.verbose);
}
