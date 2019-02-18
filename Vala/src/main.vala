void main ( string[] args ) {
    try {
        Parameters p = new Parameters ( args );
        FileStream f = FileStream.open ( p.in, "r" );
        if ( f == null ) {
            throw new Error.FILE_NOT_FOUND ( "file not found" );
        }
        Solver s = new Solver ( f, p.verbose );
        s.solve ();
        s.write_solution_to_file ( p.out );
    } catch ( Error e ) {
        stderr.printf ( "An exception occurs %s\n", e.message );
    }
}
