void solver_tests () {
    Test.add_func ( "/constructor1_solver_test", constructor1_solver_test );
    Test.add_func ( "/constructor2_solver_test", constructor2_solver_test );
    Test.add_func ( "/constructor3_solver_test", constructor3_solver_test );
    Test.add_func ( "/constructor4_solver_test", constructor4_solver_test );
    Test.add_func ( "/solve0_solver_test", solve0_solver_test );
    Test.add_func ( "/solve1_solver_test", solve1_solver_test );
    Test.add_func ( "/solve2_solver_test", solve2_solver_test );
    Test.add_func ( "/solve3_solver_test", solve3_solver_test );
    Test.add_func ( "/solve4_solver_test", solve4_solver_test );
    Test.add_func ( "/solve5_solver_test", solve5_solver_test );
    Test.add_func ( "/solve6_solver_test", solve6_solver_test );
    Test.add_func ( "/solve7_solver_test", solve7_solver_test );
    Test.add_func ( "/solve8_solver_test", solve8_solver_test );
    Test.add_func ( "/solve9_solver_test", solve9_solver_test );
    Test.add_func ( "/solve10_solver_test", solve10_solver_test );
    Test.add_func ( "/solve11_solver_test", solve11_solver_test );
    Test.add_func ( "/solve12_solver_test", solve12_solver_test );
    Test.add_func ( "/solve13_solver_test", solve13_solver_test );
}

void constructor1_solver_test () {
    try {
        var in = generate_filestream ( "1 1\n1 2" );
        Solver p = new Solver ( in );
        ( void ) p;
    } catch {
        assert ( false );
    }
}

void constructor2_solver_test () {
    bool ok = false;
    try {
        var in = generate_filestream ( "1 1\nab 2" );
        Solver p = new Solver ( in );
        ( void ) p;
    } catch {
        ok = true;
    }
    assert ( ok );
}

void constructor3_solver_test () {
    bool ok = false;
    try {
        var in = generate_filestream ( "-1 1\n2 2" );
        Solver p = new Solver ( in );
        ( void ) p;
    } catch {
        ok = true;
    }
    assert ( ok );
}

void constructor4_solver_test () {
    bool ok = false;
    try {
        var in = generate_filestream ( "1 -1\n2 2" );
        Solver p = new Solver ( in );
        ( void ) p;
    } catch {
        ok = true;
    }
    assert ( ok );
}

void solve0_solver_test () {
    try {
        var in = generate_filestream ( "1   1 \n\n 2 4" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( 2.0, 0 ) };
        NumberSolutions expected_number_solutions = NumberSolutions.ONE;
        string[] ? expected_general_solution = null;

        var actual_partial_solution = p.get_solution_partial ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( expected_general_solution == p.get_solution_general () );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
    } catch {
        assert ( false );
    }
}

void solve1_solver_test () {
    try {
        var in = generate_filestream ( "1 1\n2 4" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( 2.0, 0 ) };
        NumberSolutions expected_number_solutions = NumberSolutions.ONE;
        string[] ? expected_general_solution = null;

        var actual_partial_solution = p.get_solution_partial ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( expected_general_solution == p.get_solution_general () );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
    } catch {
        assert ( false );
    }
}

void solve2_solver_test () {
    try {
        var in = generate_filestream ( "2 2\n1 2 3\n4 5 6" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( -1.0, 0 ),
                          new ComplexNumber ( 2.0, 0 )
        };
        NumberSolutions expected_number_solutions = NumberSolutions.ONE;
        string[] ? expected_general_solution = null;

        var actual_partial_solution = p.get_solution_partial ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( expected_general_solution == p.get_solution_general () );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
    } catch {
        assert ( false );
    }
}

void solve3_solver_test () {
    try {
        var in = generate_filestream ( "2 2\n4 5 7\n3 9 9" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( 0.85714, 0 ),
                          new ComplexNumber ( 0.71429, 0 )
        };
        NumberSolutions expected_number_solutions = NumberSolutions.ONE;
        string[] ? expected_general_solution = null;

        var actual_partial_solution = p.get_solution_partial ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( expected_general_solution == p.get_solution_general () );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
    } catch {
        assert ( false );
    }
}

void solve4_solver_test () {
    try {
        var in = generate_filestream ( "3 3\n1 1 2 9\n2 4 -3 1\n3 6 -5 0" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( 1.0, 0 ),
                          new ComplexNumber ( 2.0, 0 ), new ComplexNumber ( 3.0, 0 )
        };
        NumberSolutions expected_number_solutions = NumberSolutions.ONE;
        string[] ? expected_general_solution = null;

        var actual_partial_solution = p.get_solution_partial ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( expected_general_solution == p.get_solution_general () );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
    } catch {
        assert ( false );
    }
}

void solve5_solver_test () {
    try {
        var in = generate_filestream ( "2 2\n0 1 1\n1 0 1" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( 1.0, 0 ),
                          new ComplexNumber ( 1.0, 0 )
        };
        NumberSolutions expected_number_solutions = NumberSolutions.ONE;
        string[] ? expected_general_solution = null;

        var actual_partial_solution = p.get_solution_partial ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( expected_general_solution == p.get_solution_general () );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
    } catch {
        assert ( false );
    }
}

void solve6_solver_test () {
    try {
        var in = generate_filestream ( "2 2\n0 1 1\n0 2 2" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( 0.0, 0 ),
                          new ComplexNumber ( 1.0, 0 )
        };
        NumberSolutions expected_number_solutions = NumberSolutions.MANY;
        string[] expected_general_solution = new string[] {"x1", "1"};

        var actual_partial_solution = p.get_solution_partial ();
        var actual_general_solution = p.get_solution_general ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
        assert ( actual_general_solution.length == expected_general_solution.length );
        for ( int i = 0; i < actual_general_solution.length; ++i ) {
            assert ( actual_general_solution[i] == expected_general_solution[i] );
        }
    } catch {
        assert ( false );
    }
}

void solve7_solver_test () {
    try {
        var in = generate_filestream ( "2 2\n0 1 1\n0 2 3" );
        Solver p = new Solver ( in );
        p.solve ();

        NumberSolutions expected_number_solutions = NumberSolutions.NONE;

        var actual_partial_solution = p.get_solution_partial ();
        var actual_general_solution = p.get_solution_general ();

        assert ( p.number_solutions == expected_number_solutions );
        assert ( actual_partial_solution == null );
        assert ( actual_general_solution == null );
    } catch {
        assert ( false );
    }
}

void solve8_solver_test () {
    try {
        var in = generate_filestream ( "3 4\n0 1 2 9\n0 1 3 1\n1 0 6 0\n2 0 2 0" );
        Solver p = new Solver ( in );
        p.solve ();

        NumberSolutions expected_number_solutions = NumberSolutions.NONE;

        var actual_partial_solution = p.get_solution_partial ();
        var actual_general_solution = p.get_solution_general ();

        assert ( p.number_solutions == expected_number_solutions );
        assert ( actual_partial_solution == null );
        assert ( actual_general_solution == null );
    } catch {
        assert ( false );
    }
}

void solve9_solver_test () {
    try {
        var in = generate_filestream ( "3 1\n1 1 2 9" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( 9.0, 0 ),
                          new ComplexNumber ( 0, 0 ),
                          new ComplexNumber ( 0, 0 )
        };
        NumberSolutions expected_number_solutions = NumberSolutions.MANY;
        string[] expected_general_solution = new string[] {"9 - x2 - x3 * (2)", "x2", "x3"};

        var actual_partial_solution = p.get_solution_partial ();
        var actual_general_solution = p.get_solution_general ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
        assert ( actual_general_solution.length == expected_general_solution.length );
        for ( int i = 0; i < actual_general_solution.length; ++i ) {
            assert ( actual_general_solution[i] == expected_general_solution[i] );
        }
    } catch {
        assert ( false );
    }
}

void solve10_solver_test () {
    try {
        var in = generate_filestream ( "4 4\n1 0 0 5 0\n0 0 0 0 0\n0 0 1 4 6\n0 0 5 5 5" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {
            new ComplexNumber ( -8.3333333, 0 ),new ComplexNumber ( 0, 0 ),
            new ComplexNumber ( -0.6666667, 0 ), new ComplexNumber ( 1.6666667, 0 )
        };
        NumberSolutions expected_number_solutions = NumberSolutions.MANY;
        string[] expected_general_solution = new string[] {"-8.3333", "x2", "-0.6667", "1.6667"};

        var actual_partial_solution = p.get_solution_partial ();
        var actual_general_solution = p.get_solution_general ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
        assert ( actual_general_solution.length == expected_general_solution.length );
        for ( int i = 0; i < actual_general_solution.length; ++i ) {
            assert ( actual_general_solution[i] == expected_general_solution[i] );
        }
    } catch {
        assert ( false );
    }
}

void solve11_solver_test () {
    try {
        var in = generate_filestream ( "4 4\n2 3 -1 1 1\n8 12 -9 8 3\n4 6 3 -2 3\n2 3 9 -7 3" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( 0.6, 0 ),
                          new ComplexNumber ( 0, 0 ), new ComplexNumber ( 0.2, 0 ), new ComplexNumber ( 0, 0 )
        };
        NumberSolutions expected_number_solutions = NumberSolutions.MANY;
        string[] expected_general_solution = new string[] {"0.6 - x2 * (1.5) - x4 * (0.1)", "x2",
                "0.2 - x4 * (-0.8)", "x4"
                                                          };

        var actual_partial_solution = p.get_solution_partial ();
        var actual_general_solution = p.get_solution_general ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
        assert ( actual_general_solution.length == expected_general_solution.length );
        for ( int i = 0; i < actual_general_solution.length; ++i ) {
            assert ( actual_general_solution[i] == expected_general_solution[i] );
        }
    } catch {
        assert ( false );
    }
}

void solve12_solver_test () {
    try {
        var in = generate_filestream ( "3 3\n1+2i -1.5-1.1i 2.12 91+5i\n" +
                                       "-1+3i 1.2+3.5i -3.3 1+15i\n12.31 1.3-5i 12.3i -78.3i" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {
            new ComplexNumber ( 6.73335286, -22.99754223 ),
            new ComplexNumber ( -1.7976071, 2.08404919 ),
            new ComplexNumber ( 15.69938581, 7.3960106 )
        };
        NumberSolutions expected_number_solutions = NumberSolutions.ONE;
        string[] ? expected_general_solution = null;

        var actual_partial_solution = p.get_solution_partial ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( expected_general_solution == p.get_solution_general () );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
    } catch {
        assert ( false );
    }
}

void solve13_solver_test () {
    try {
        var in = generate_filestream ( "1\t1\t2\t4" );
        Solver p = new Solver ( in );
        p.solve ();

        ComplexNumber[] expected_partial_solution = new ComplexNumber[] {new ComplexNumber ( 2.0, 0 ) };
        NumberSolutions expected_number_solutions = NumberSolutions.ONE;
        string[] ? expected_general_solution = null;

        var actual_partial_solution = p.get_solution_partial ();

        assert ( expected_number_solutions == p.number_solutions );
        assert ( expected_general_solution == p.get_solution_general () );
        assert ( actual_partial_solution.length == expected_partial_solution.length );
        for ( int i = 0; i < actual_partial_solution.length; ++i ) {
            assert ( actual_partial_solution[i].equals ( expected_partial_solution[i] ) );
        }
    } catch {
        assert ( false );
    }
}
