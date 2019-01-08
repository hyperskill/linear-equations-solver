void complex_tests () {
    Test.add_func ( "/equals1_complex_test", equals1_complex_test );
    Test.add_func ( "/equals2_complex_test", equals2_complex_test );
    Test.add_func ( "/equals3_complex_test", equals3_complex_test );
    Test.add_func ( "/default_constructor_complex_test", default_constructor_complex_test );
    Test.add_func ( "/constructor1_complex_test", constructor1_complex_test );
    Test.add_func ( "/to_string1_complex_test", to_string1_complex_test );
    Test.add_func ( "/to_string2_complex_test", to_string2_complex_test );
    Test.add_func ( "/to_string3_complex_test", to_string3_complex_test );
    Test.add_func ( "/to_string4_complex_test", to_string4_complex_test );
    Test.add_func ( "/to_string5_complex_test", to_string5_complex_test );
    Test.add_func ( "/from_string1_complex_test", from_string1_complex_test );
    Test.add_func ( "/from_string2_complex_test", from_string2_complex_test );
    Test.add_func ( "/from_string3_complex_test", from_string3_complex_test );
    Test.add_func ( "/from_string4_complex_test", from_string4_complex_test );
    Test.add_func ( "/from_string5_complex_test", from_string5_complex_test );
    Test.add_func ( "/from_string6_complex_test", from_string6_complex_test );
    Test.add_func ( "/from_string7_complex_test", from_string7_complex_test );
    Test.add_func ( "/from_string8_complex_test", from_string8_complex_test );
    Test.add_func ( "/from_string9_complex_test", from_string9_complex_test );
    Test.add_func ( "/from_string10_complex_test", from_string10_complex_test );
    Test.add_func ( "/from_string11_complex_test", from_string11_complex_test );
    Test.add_func ( "/add_complex_test", add_complex_test );
    Test.add_func ( "/multiply_complex_test", multiply_complex_test );
    Test.add_func ( "/conjugate_complex_test", conjugate_complex_test );
    Test.add_func ( "/divide_complex_test", divide_complex_test );
}

void equals1_complex_test () {
    ComplexNumber a = new ComplexNumber ( 1.0, 2.0 );
    ComplexNumber b = new ComplexNumber ( 1.0, 2.0 );

    assert ( a.equals ( b ) );
}

void equals2_complex_test () {
    ComplexNumber a = new ComplexNumber ( 1.0, 2.0 );
    ComplexNumber b = new ComplexNumber ( 1.1, 2.0 );

    assert ( !a.equals ( b ) );
}

void equals3_complex_test () {
    ComplexNumber a = new ComplexNumber ( 1.0, 2.1 );
    ComplexNumber b = new ComplexNumber ( 1.0, 2.0 );

    assert ( !a.equals ( b ) );
}

void default_constructor_complex_test () {
    ComplexNumber a = new ComplexNumber ();

    assert ( Math.fabs ( 0.0 - a.real ) < ComplexNumber.EPSILON );
    assert ( Math.fabs ( 0.0 - a.imag ) < ComplexNumber.EPSILON );
}

void constructor1_complex_test () {
    ComplexNumber a = new ComplexNumber ( 1.0, 3.5 );

    assert ( Math.fabs ( 1.0 - a.real ) < ComplexNumber.EPSILON );
    assert ( Math.fabs ( 3.5 - a.imag ) < ComplexNumber.EPSILON );
}

void to_string1_complex_test () {
    ComplexNumber a = new ComplexNumber ( 1.0, 0.0 );

    assert ( "1" == a.to_string () );
}

void to_string2_complex_test () {
    ComplexNumber a = new ComplexNumber ( 0.0, 1.0 );

    assert ( "1i" == a.to_string () );
}

void to_string3_complex_test () {
    ComplexNumber a = new ComplexNumber ( -2.4, 1.5 );

    assert ( "-2.4+1.5i" == a.to_string () );
}

void to_string4_complex_test () {
    ComplexNumber a = new ComplexNumber ( 2.4, -1.5 );

    assert ( "2.4-1.5i" == a.to_string () );
}

void to_string5_complex_test () {
    ComplexNumber a = new ComplexNumber ( 0.0, 0.0 );

    assert ( "0" == a.to_string () );
}

void from_string1_complex_test () {
    bool ok = false;
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "gbc" );
        ( void ) a;
    } catch {
        ok = true;
    }

    assert ( ok );
}

void from_string2_complex_test () {
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "-1.3" );
        ComplexNumber e = new ComplexNumber ( -1.3, 0.0 );

        assert ( a.equals ( e ) );
    } catch {
        assert ( false );
    }
}

void from_string3_complex_test () {
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "2.5i" );
        ComplexNumber e = new ComplexNumber ( 0.0, 2.5 );

        assert ( a.equals ( e ) );
    } catch {
        assert ( false );
    }
}

void from_string4_complex_test () {
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "1.3-2.5i" );
        ComplexNumber e = new ComplexNumber ( 1.3, -2.5 );

        assert ( a.equals ( e ) );
    } catch {
        assert ( false );
    }
}

void from_string5_complex_test () {
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "1" );
        ComplexNumber e = new ComplexNumber ( 1.0, 0.0 );

        assert ( a.equals ( e ) );
    } catch {
        assert ( false );
    }
}

void from_string6_complex_test () {
    bool ok = false;
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "1.3-2.5" );
        ( void ) a;
    } catch {
        ok = true;
    }

    assert ( ok );
}

void from_string7_complex_test () {
    bool ok = false;
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "1.3i-2.5" );
        ( void ) a;
    } catch {
        ok = true;
    }

    assert ( ok );
}

void from_string8_complex_test () {
    bool ok = false;
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "1.3ij" );
        ( void ) a;
    } catch {
        ok = true;
    }

    assert ( ok );
}

void from_string9_complex_test () {
    bool ok = false;
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "1.3+3.5546ij" );
        ( void ) a;
    } catch {
        ok = true;
    }

    assert ( ok );
}

void from_string10_complex_test () {
    bool ok = false;
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "1.3ji" );
        ( void ) a;
    } catch {
        ok = true;
    }

    assert ( ok );
}

void from_string11_complex_test () {
    bool ok = false;
    try {
        ComplexNumber a = new ComplexNumber.from_string ( "1.3+3.5546ji" );
        ( void ) a;
    } catch {
        ok = true;
    }

    assert ( ok );
}

void add_complex_test () {
    ComplexNumber a = new ComplexNumber ( 3.0, -5.0 );
    ComplexNumber b = new ComplexNumber ( 4.0, 2.0 );

    ComplexNumber expected = new ComplexNumber ( 7.0, -3.0 );
    ComplexNumber actual = ComplexNumber.add ( a, b );

    assert ( expected.equals ( actual ) );
}

void multiply_complex_test () {
    ComplexNumber a = new ComplexNumber ( 3.0, 2.0 );
    ComplexNumber b = new ComplexNumber ( 1.0, 7.0 );

    ComplexNumber expected = new ComplexNumber ( -11.0, 23.0 );
    ComplexNumber actual = ComplexNumber.multiply ( a, b );

    assert ( expected.equals ( actual ) );
}

void conjugate_complex_test () {
    ComplexNumber a = new ComplexNumber ( 3.0, 2.0 );

    ComplexNumber expected = new ComplexNumber ( 3.0, -2.0 );
    ComplexNumber actual = a.conjugate ();

    assert ( expected.equals ( actual ) );
}

void divide_complex_test () {
    ComplexNumber a = new ComplexNumber ( 2.0, 3.0 );
    ComplexNumber b = new ComplexNumber ( 4.0, -5.0 );

    ComplexNumber expected = new ComplexNumber ( -7.0 / 41.0, 22.0 / 41.0 );
    ComplexNumber actual = ComplexNumber.divide ( a, b );

    assert ( expected.equals ( actual ) );
}

