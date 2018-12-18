package solver;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Solver {
    private double[][] matrix;
    private double[] solution;
    private boolean verbose;

    public Solver(Scanner sc){
        this(sc, false);
    }

    public Solver(@NotNull Scanner sc, boolean verbose) {
        final int n = sc.nextInt();
        matrix = new double[n][n+1];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n+1; ++j) {
                matrix[i][j] = sc.nextDouble();
            }
        }
        solution = new double[n];
        this.verbose = verbose;
    }

    public Solver(String in, boolean verbose) throws FileNotFoundException {
        this(new Scanner(new File(in)), verbose);
    }

    public int getSize() {
        return matrix.length;
    }

    private void multiplyRow(int row, double k) {
        if (verbose) {
            System.out.printf("%f * R%d -> R%d\n", k, row+1, row+1);
        }
        final int n = matrix[row].length;
        for (int i = 0; i < n; ++i) {
            matrix[row][i] *= k;
        }
    }

    private void addKRow1ToRow2(double k, int row1, int row2) {
        if (verbose) {
            System.out.printf("%f * R%d +R%d -> R%d\n", k, row1+1, row2+1, row2+1);
        }
        final int n = matrix[row1].length;
        for (int i = 0; i < n; ++i) {
            matrix[row2][i] += k*matrix[row1][i];
        }
    }

    public void solve() {
        if (verbose) {
            System.out.println("Start solving the equation.");
            System.out.println("Rows manipulation:");
        }
        final int n = matrix.length;
        for (int i = 0; i < n; ++i) {
            if (matrix[i][i] != 1.0) {
                final double k = 1.0 / matrix[i][i];
                multiplyRow(i, k);
            }
            for (int j = i + 1; j < n; ++j) {
                final double k = -matrix[j][i];
                addKRow1ToRow2(k, i, j);
            }
        }
        for (int i = n-1; i >= 0; --i) {
            for (int j = i - 1; j >= 0; --j) {
                final double k = -matrix[j][i];
                addKRow1ToRow2(k, i, j);
            }
        }
        for (int i = 0; i < n; ++i) {
            solution[i] = matrix[i][n];
        }
        if (verbose) {
            System.out.println("The solution is: " + Arrays.toString(solution));
        }
    }

    public double[] getSolution() {
        return solution;
    }

    public void writeSolutionToFile(String out) throws FileNotFoundException {
        File file = new File(out);
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.printf("(%f", solution[0]);
        for (int i = 1; i < solution.length; ++i) {
            printWriter.printf(", %f", solution[i]);
        }
        printWriter.println(")");
        printWriter.close();
    }
}
