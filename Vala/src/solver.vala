public class Solver {
    private ComplexNumber ZERO = new ComplexNumber ( 0.0, 0.0 );
    private ComplexNumber ONE = new ComplexNumber ( 1.0, 0.0 );
    private ComplexNumber MINUS_ONE = new ComplexNumber ( -1.0, 0.0 );

    private int number_equations;
    private int number_variables;
    private ComplexNumber[,] matrix;
    private ComplexNumber[] solution_partial;
    private string[] solution_general;
    private int[] solution_indexes;
    private bool verbose;
    public NumberSolutions number_solutions {get; private set; default = NumberSolutions.ONE;}

    public Solver ( FileStream in, bool verbose = false ) throws Error
    requires ( in != null ) {
        int real_number_equations = -1;
        int result = in.scanf ( "%d%d", &number_variables, &real_number_equations );
        if ( result != 2 || number_variables <= 0 || real_number_equations <= 0 ) {
            throw new Error.WRONG_DATA ( "wrong data" );
        }
        number_equations = ( real_number_equations < number_variables ) ? number_variables :
                           real_number_equations;
        matrix = new ComplexNumber[number_equations, number_variables + 1];
        for ( int i = 0; i < real_number_equations; ++i ) {
            for ( int j = 0; j < number_variables + 1; ++j ) {
                string ? temp = Util.read_next ( in );
                if ( temp == null ) {
                    throw new Error.WRONG_DATA ( "wrong data" );
                }
                matrix[i, j] = new ComplexNumber.from_string ( temp );
            }
        }
        for ( int i = real_number_equations; i < number_equations; ++i ) {
            for ( int j = 0; j < number_variables + 1; ++j ) {
                matrix[i, j] = new ComplexNumber ();
            }
        }
        solution_partial = new ComplexNumber[number_variables];
        solution_general = new string[number_variables];
        solution_indexes = new int[number_variables];
        for ( int i = 0; i < number_variables; ++i ) {
            solution_indexes[i] = i;
        }
        this.verbose = verbose;
    }

    public string[] ? get_solution_general () {
        if ( number_solutions != NumberSolutions.MANY ) {
            return null;
        }
        return solution_general;
    }

    public ComplexNumber[] ? get_solution_partial () {
        if ( number_solutions == NumberSolutions.NONE ) {
            return null;
        }
        return solution_partial;
    }

    public void solve () {
        if ( verbose ) {
            stdout.printf ( "Start solving the equation.\n" );
            stdout.printf ( "Rows manipulation:\n" );
        }
        gaus_first_step ();
        if ( number_solutions != NumberSolutions.NONE ) {
            gaus_second_step ();
            generate_solutions ();
            check_that_solution_is_sane ();
        }
        print_solution ();
    }

    public void write_solution_to_file ( string o ) throws Error
    requires ( o != null ) {
        FileStream file = FileStream.open ( o, "w" );
        if ( file == null ) {
            throw new Error.FILE_CANT_CREATE ( "can't create file" );
        }
        print_solution_internal ( file );
        file.flush ();
        if ( verbose ) {
            stdout.printf ( "Saved to file %s\n", o );
        }
    }

    private void add_k_row1_to_row2 ( ComplexNumber k, int row1, int row2 )
    requires ( k != null ) {
        if ( verbose ) {
            stdout.printf ( "%s * R%d +R%d -> R%d\n", k.to_string (), row1 + 1, row2 + 1, row2 + 1 );
        }
        for ( int i = 0; i < number_variables + 1; ++i ) {
            var temp = ComplexNumber.multiply ( k, matrix[row1, i] );
            matrix[row2, i] = ComplexNumber.add ( temp, matrix[row2, i] );
        }
    }

    private void check_that_solution_is_sane () {
        for ( int i = number_variables; i < number_equations; ++i ) {
            ComplexNumber sum = new ComplexNumber ( 0.0, 0.0 );
            for ( int j = 0; j < number_variables; ++j ) {
                var temp = ComplexNumber.multiply ( solution_partial[solution_indexes[j]], matrix[i,
                                                    solution_indexes[j]] );
                sum = ComplexNumber.add ( sum, temp );
            }
            if ( !sum.equals ( matrix[i, number_variables] ) ) {
                number_solutions = NumberSolutions.NONE;
                return;
            }
        }
    }

    private void divide_row ( int row, ComplexNumber k )
    requires ( k != null ) {
        if ( verbose ) {
            stdout.printf ( "R%d / %s -> R%d\n", row + 1, k.to_string (), row + 1 );
        }
        for ( int i = 0; i < number_variables + 1; ++i ) {
            matrix[row, i] = ComplexNumber.divide ( matrix[row, i], k );
        }
    }

    private void gaus_first_step () {
        for ( int i = 0; i < number_variables; ++i ) {
            if ( matrix[i, i].equals ( ZERO ) ) {
                bool found_non_zero_element = false;
                for ( int j = i + 1; j < number_equations; ++j ) {
                    if ( !matrix[j, i].equals ( ZERO ) ) {
                        swap_rows ( i, j );
                        found_non_zero_element = true;
                        break;
                    }
                }
                if ( !found_non_zero_element ) {
                    for ( int j = i + 1; j < number_variables; ++j ) {
                        if ( !matrix[i, j].equals ( ZERO ) ) {
                            swap_columns ( i, j );
                            found_non_zero_element = true;
                            break;
                        }
                    }
                }

                if ( !found_non_zero_element ) {
                    for ( int k = i + 1; !found_non_zero_element && k < number_variables; ++k ) {
                        for ( int j = i + 1; j < number_equations; ++j ) {
                            if ( !matrix[j, k].equals ( ZERO ) ) {
                                swap_columns ( k, i );
                                swap_rows ( j, i );
                                found_non_zero_element = true;
                                break;
                            }
                        }
                    }
                }

                if ( !found_non_zero_element ) {
                    if ( matrix[i, number_variables].equals ( ZERO ) ) {
                        number_solutions = NumberSolutions.MANY;
                        continue;
                    } else {
                        number_solutions = NumberSolutions.NONE;
                        return;
                    }
                }
            }

            if ( !matrix[i, i].equals ( ONE ) ) {
                var k = matrix[i, i];
                divide_row ( i, k );
            }
            for ( int j = i + 1; j < number_equations && j < number_variables; ++j ) {
                var k = ComplexNumber.multiply ( MINUS_ONE, matrix[j, i] );
                if ( !k.equals ( ZERO ) ) {
                    add_k_row1_to_row2 ( k, i, j );
                }
            }
        }
    }

    private void gaus_second_step () {
        for ( int i = number_variables - 1; i >= 0; --i ) {
            for ( int j = i - 1; j >= 0; --j ) {
                var k = ComplexNumber.multiply ( MINUS_ONE, matrix[j, i] );
                if ( !k.equals ( ZERO ) ) {
                    add_k_row1_to_row2 ( k, i, j );
                }
            }
        }
    }

    private void generate_solutions () {
        for ( int i = 0; i < number_equations && i < number_variables; ++i ) {
            solution_partial[solution_indexes[i]] = matrix[i, number_variables];
            if ( matrix[i, i].equals ( ZERO ) ) {
                solution_general[solution_indexes[i]] = "x%d".printf ( solution_indexes[i] + 1 );
            } else {
                solution_general[solution_indexes[i]] = matrix[i, number_variables].to_string ();
                for ( int j = i + 1; j < number_variables; ++j ) {
                    if ( matrix[i, j].equals ( ONE ) ) {
                        solution_general[solution_indexes[i]] = "%s - x%d".printf (
                                solution_general[solution_indexes[i]], ( solution_indexes[j] + 1 ) );
                    } else if ( !matrix[i, j].equals ( ZERO ) ) {
                        solution_general[solution_indexes[i]] = "%s - x%d * (%s)".printf (
                                solution_general[solution_indexes[i]], ( solution_indexes[j] + 1 ),
                                matrix[i, j].to_string () );
                    }
                }
            }
        }
    }

    private void print_solution () {
        if ( verbose ) {
            print_solution_internal ( stdout );
        }
    }

    private void print_solution_internal ( FileStream out )
    requires ( out != null ) {
        switch ( number_solutions ) {
        case NumberSolutions.NONE:
            out.printf ( "There are no solutions\n" );
            break;
        case NumberSolutions.ONE:
            out.printf ( "(%s", solution_partial[0].to_string () );
            for ( int i = 1; i < solution_partial.length; ++i ) {
                out.printf ( ", %s", solution_partial[i].to_string () );
            }
            out.printf ( ")\n" );
            break;
        case NumberSolutions.MANY:
            out.printf ( "(%s", solution_general[0] );
            for ( int i = 1; i < solution_general.length; ++i ) {
                out.printf ( ", %s", solution_general[i] );
            }
            out.printf ( ")\n" );
            break;
        default:
            assert ( false );
            break;
        }
    }

    private void swap_columns ( int column1, int column2 ) {
        if ( verbose ) {
            stdout.printf ( "C%d <-> C%d\n", column1 + 1, column2 + 1 );
        }
        for ( int i = 0; i < number_equations; ++i ) {
            var temp1 = matrix[i, column1];
            matrix[i, column1] = matrix[i, column2];
            matrix[i, column2] = temp1;
        }
        int temp2 = solution_indexes[column1];
        solution_indexes[column1] = solution_indexes[column2];
        solution_indexes[column2] = temp2;
    }

    private void swap_rows ( int row1, int row2 ) {
        if ( verbose ) {
            stdout.printf ( "R%d <-> R%d\n", row1 + 1, row2 + 1 );
        }
        for ( int i = 0; i < number_variables + 1; ++i ) {
            var temp = matrix[row1, i];
            matrix[row1, i] = matrix[row2, i];
            matrix[row2, i] = temp;
        }
    }
}
