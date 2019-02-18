void util_tests () {
    Test.add_func ( "/read_next1_utul_test", read_next1_utul_test );
    Test.add_func ( "/read_next2_utul_test", read_next2_utul_test );
    Test.add_func ( "/round1_utul_test", round1_utul_test );
    Test.add_func ( "/round2_utul_test", round2_utul_test );
    Test.add_func ( "/round3_utul_test", round3_utul_test );
    Test.add_func ( "/round4_utul_test", round4_utul_test );

}

void read_next1_utul_test () {
    var in = generate_filestream ( "first second" );
    string ? s = Util.read_next ( in );

    assert ( s != null );
    assert ( s == "first" );
}

void read_next2_utul_test () {
    var in = generate_filestream ( "\n\n\t  first \n\n\tsecond" );
    string ? s = Util.read_next ( in );

    assert ( s != null );
    assert ( s == "first" );
}

void round1_utul_test () {
    double rounded = Util.round ( 2.4 );

    assert ( rounded == 2.0 );
}

void round2_utul_test () {
    double rounded = Util.round ( 2.6 );

    assert ( rounded == 3.0 );
}

void round3_utul_test () {
    double rounded = Util.round ( -2.4 );

    assert ( rounded == -2.0 );
}

void round4_utul_test () {
    double rounded = Util.round ( -2.6 );

    assert ( rounded == -3.0 );
}
