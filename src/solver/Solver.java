package solver;

import java.util.Scanner;

public class Solver {
    private double matrix[][];
    public Solver(Scanner sc) {
        final int n = sc.nextInt();
        matrix = new double[n][n+1];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = sc.nextDouble();
            }
        }
    }

    public int getSize() {
        return matrix.length;
    }
}
