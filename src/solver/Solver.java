package solver;

import java.util.Scanner;

public class Solver {
    private double[][] matrix;
    private double[] solution;
    public Solver(Scanner sc) {
        final int n = sc.nextInt();
        matrix = new double[n][n+1];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n+1; ++j) {
                matrix[i][j] = sc.nextDouble();
            }
        }
        solution = new double[n];
    }

    public int getSize() {
        return matrix.length;
    }

    private void multiplyRow(int row, double k) {
        final int n = matrix[row].length;
        for (int i = 0; i < n; ++i) {
            matrix[row][i] *= k;
        }
    }

    public void solve() {
        final int n = matrix.length;
        for (int i = 0; i < n; ++i) {
            if (matrix[i][i] != 1.0) {
                final double k = 1.0 / matrix[i][i];
                multiplyRow(i, k);
            }
        }
        for (int i = 0; i < n; ++i) {
            solution[i] = matrix[i][n];
        }
    }

    public double[] getSolution() {
        return solution;
    }
}
