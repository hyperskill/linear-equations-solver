void main ( string[] args ) {
    Test.init ( ref args );
    parameters_tests ();
    complex_tests ();
    util_tests ();
    solver_tests ();
    Test.run ();
}
