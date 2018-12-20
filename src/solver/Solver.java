package solver;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solver {
    private static final double epsilon = 0.0000001;

    private int numberEquations;
    private int numberVariables;
    private double[][] matrix;
    private double[] solution;
    private String[] solutionGeneral;
    private int[] solutionIndexes;
    private boolean verbose;
    private NumberSolutions numberSolutions = NumberSolutions.ONE;

    public Solver(Scanner sc){
        this(sc, false);
    }

    public Solver(@NotNull Scanner sc, boolean verbose) {
        numberVariables = sc.nextInt();
        final int realNumberEquations = sc.nextInt();
        numberEquations = (realNumberEquations < numberVariables) ? numberVariables : realNumberEquations;
        matrix = new double[numberEquations][numberVariables +1];
        for (int i = 0; i < realNumberEquations; ++i) {
            for (int j = 0; j < numberVariables +1; ++j) {
                matrix[i][j] = sc.nextDouble();
            }
        }
        solution = new double[numberVariables];
        solutionGeneral = new String[numberVariables];
        solutionIndexes = IntStream.range(0, numberVariables).toArray();
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
        for (int i = 0; i < numberVariables +1; ++i) {
            matrix[row2][i] += k*matrix[row1][i];
        }
    }

    private void swapRows(int row1, int row2) {
        if (verbose) {
            System.out.printf("R%d <-> R%d\n", row1+1, row2+1);
        }
        for (int i = 0; i < numberVariables +1; ++i) {
            final double temp = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = temp;
        }
    }


    public void solve() {
        if (verbose) {
            System.out.println("Start solving the equation.");
            System.out.println("Rows manipulation:");
        }
        for (int i = 0; i < numberVariables; ++i) {
            if (Math.abs(matrix[i][i]) < epsilon) {
                boolean notFound = true;
                for (int j = i+1; j < numberEquations; ++j) {
                    if (Math.abs(matrix[j][i]) > epsilon) {
                        swapRows(i, j);
                        break;
                    }
                }
                if (notFound) {
                    for (int j = i+1; j < numberEquations; ++j) {
                        if (Math.abs(matrix[i][j]) > epsilon) {
                            swapColumns(i, j);
                            notFound = false;
                            break;
                        }
                    }
                }

                if (notFound) {
                    for (int k = i+1; notFound && k < numberVariables; ++k) {
                        for (int j = i+1; j < numberEquations; ++j) {
                            if (Math.abs(matrix[j][k]) > epsilon) {
                                swapColumns(k, i);
                                swapRows(j, i);
                                notFound = false;
                                break;
                            }
                        }
                    }
                }

                if (notFound) {
                    if (Math.abs(matrix[i][numberEquations]) < epsilon) {
                        numberSolutions = NumberSolutions.MANY;
                        continue;
                    } else {
                        numberSolutions = NumberSolutions.NONE;
                        printSolution();
                        return;
                    }
                }
            }

            if (Math.abs(matrix[i][i] - 1.0) > epsilon) {
                final double k = 1.0 / matrix[i][i];
                multiplyRow(i, k);
            }
            for (int j = i + 1; j < numberEquations && j < numberVariables; ++j) {
                final double k = -matrix[j][i];
                if (Math.abs(k) >= epsilon) {
                    addKRow1ToRow2(k, i, j);
                }
            }
        }
        for (int i = numberVariables - 1; i >= 0; --i) {
            for (int j = i - 1; j >= 0; --j) {
                final double k = -matrix[j][i];
                if (Math.abs(k) >= epsilon) {
                    addKRow1ToRow2(k, i, j);
                }
            }
        }
        for (int i = 0; i < numberEquations && i < numberVariables; ++i) {
            solution[solutionIndexes[i]] = matrix[i][numberVariables];
            if (Math.abs(matrix[i][i]) < epsilon ) {
                solutionGeneral[solutionIndexes[i]] = "x" + (solutionIndexes[i]+1);
            } else {
                solutionGeneral[solutionIndexes[i]] = String.format("%.4f", matrix[i][numberVariables]);
                for (int j = i + 1; j < numberVariables; ++j) {
                    if (Math.abs(matrix[i][j]) > epsilon) {
                        solutionGeneral[solutionIndexes[i]] = solutionGeneral[solutionIndexes[i]] + " - x" +
                                (solutionIndexes[j]+1) + " * (" + String.format("%.4f",matrix[i][j]) + ")" ;
                    }
                }
            }
        }
        for (int i = numberVariables; i < numberEquations; ++i) {
            double sum = 0.0;
            for (int j = 0; j < numberVariables; ++j) {
                sum += solution[solutionIndexes[j]] * matrix[i][solutionIndexes[j]];
            }
            if (Math.abs(sum - matrix[i][numberVariables]) >= epsilon) {
                numberSolutions = NumberSolutions.NONE;
                printSolution();
                return;
            }
        }

        printSolution();
    }

    private void printSolution() {
        if (verbose) {
            printSolutionInternal(new PrintWriter(System.out, true));
        }
    }

    private void printSolutionInternal(PrintWriter printWriter) {
        if (numberSolutions == NumberSolutions.NONE) {
            printWriter.println("There are no solutions");
        } else if (numberSolutions == NumberSolutions.ONE){
            printWriter.printf("(%f", solution[0]);
            for (int i = 1; i < solution.length; ++i) {
                printWriter.printf(", %f", solution[i]);
            }
            printWriter.println(")");
        } else {
            printWriter.printf("(%s", solutionGeneral[0]);
            for (int i = 1; i < solution.length; ++i) {
                printWriter.printf(", %s", solutionGeneral[i]);
            }
            printWriter.println(")");
        }
        printWriter.flush();
    }

    private void swapColumns(int column1, int column2) {
        if (verbose) {
            System.out.printf("C%d <-> C%d\n", column1+1, column2+1);
        }
        final int n = matrix.length;
        for (int i = 0; i < n; ++i) {
            final double temp = matrix[i][column1];
            matrix[i][column1] = matrix[i][column2];
            matrix[i][column2] = temp;
        }
        final int temp = solutionIndexes[column1];
        solutionIndexes[column1] = solutionIndexes[column2];
        solutionIndexes[column2] = temp;
    }

    public double[] getParticularSolution() {
        if (numberSolutions == NumberSolutions.NONE) {
            return null;
        }
        return solution;
    }

    public NumberSolutions getNumberSolutions() {
        return numberSolutions;
    }

    public void writeSolutionToFile(String out) throws FileNotFoundException {
        File file = new File(out);
        PrintWriter printWriter = new PrintWriter(file);
        printSolutionInternal(printWriter);
        printWriter.close();
        if (verbose) {
            System.out.printf("Saved to file %s", out);
        }
    }

    public String[] getGeneralSolution() {
        if (numberSolutions != NumberSolutions.MANY) {
            return null;
        }
        return solutionGeneral;
    }
}
