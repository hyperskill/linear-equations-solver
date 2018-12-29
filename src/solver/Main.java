package solver;

public class Main {
    public static void main(String[] args) {
        try {
            final Parameters p = new Parameters(args);
            final Solver s = new Solver(p.in, p.verbose);
            s.solve();
            s.writeSolutionToFile(p.out);
        } catch (Exception e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }
}