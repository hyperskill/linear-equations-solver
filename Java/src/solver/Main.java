// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package solver;

class Main {
    public static void main(String[] args) {
        try {
            final Parameters p = new Parameters(args);
            final Solver s = new Solver(p.in, p.verbose);
            s.solve();
            s.writeSolutionToFile(p.out);
        } catch (Exception e) {
            System.out.printf("An exception occurs %s\n", e.getMessage());
        }
    }
}
