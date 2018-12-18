package solver;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            Parameters p = new Parameters(args);
            Solver s = new Solver(p.in, p.verbose);
            s.solve();
            s.writeSolutionToFile(p.out);
        } catch (FileNotFoundException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }
}