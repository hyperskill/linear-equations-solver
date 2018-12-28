package solver;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solver {
    private static final Complex zero = new Complex(0.0, 0.0);
    private static final Complex one = new Complex(1.0, 0.0);
    private static final Complex minusOne = new Complex(-1.0, 0.0);

    private int numberEquations;
    private int numberVariables;
    private Complex[][] matrix;
    private Complex[] solutionParticular;
    private String[] solutionGeneral;
    private int[] solutionIndexes;
    private boolean verbose;
    private NumberSolutions numberSolutions = NumberSolutions.ONE;

    public Solver(Scanner sc) {
        this(sc, false);
    }

    public Solver(@NotNull Scanner sc, boolean verbose) {
        numberVariables = sc.nextInt();
        final int realNumberEquations = sc.nextInt();
        numberEquations = (realNumberEquations < numberVariables) ? numberVariables : realNumberEquations;
        matrix = new Complex[numberEquations][numberVariables + 1];
        for (int i = 0; i < realNumberEquations; ++i) {
            for (int j = 0; j < numberVariables + 1; ++j) {
                matrix[i][j] = new Complex(sc.next());
            }
        }
        for (int i = realNumberEquations; i < numberEquations; ++i) {
            for (int j = 0; j < numberVariables + 1; ++j) {
                matrix[i][j] = new Complex();
            }
        }
        solutionParticular = new Complex[numberVariables];
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

    private void divideRow(int row, @NotNull Complex k) {
        if (verbose) {
            System.out.printf("R%d / %s -> R%d\n", row + 1, k.toString(), row + 1);
        }
        final int n = matrix[row].length;
        for (int i = 0; i < n; ++i) {
            matrix[row][i] = Complex.divide(matrix[row][i], k);
        }
    }

    private void addKRow1ToRow2(@NotNull Complex k, int row1, int row2) {
        if (verbose) {
            System.out.printf("%s * R%d +R%d -> R%d\n", k.toString(), row1 + 1, row2 + 1, row2 + 1);
        }
        for (int i = 0; i < numberVariables + 1; ++i) {
            final Complex temp = Complex.multiply(k, matrix[row1][i]);
            matrix[row2][i] = Complex.add(temp, matrix[row2][i]);
        }
    }

    private void swapRows(int row1, int row2) {
        if (verbose) {
            System.out.printf("R%d <-> R%d\n", row1 + 1, row2 + 1);
        }
        for (int i = 0; i < numberVariables + 1; ++i) {
            final Complex temp = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = temp;
        }
    }

    private void gausFirstStep() {
        for (int i = 0; i < numberVariables; ++i) {
            if (matrix[i][i].equals(zero)) {
                boolean notFound = true;
                for (int j = i + 1; j < numberEquations; ++j) {
                    if (!matrix[j][i].equals(zero)) {
                        swapRows(i, j);
                        break;
                    }
                }
                if (notFound) {
                    for (int j = i + 1; j < numberEquations; ++j) {
                        if (!matrix[i][j].equals(zero)) {
                            swapColumns(i, j);
                            notFound = false;
                            break;
                        }
                    }
                }

                if (notFound) {
                    for (int k = i + 1; notFound && k < numberVariables; ++k) {
                        for (int j = i + 1; j < numberEquations; ++j) {
                            if (!matrix[j][k].equals(zero)) {
                                swapColumns(k, i);
                                swapRows(j, i);
                                notFound = false;
                                break;
                            }
                        }
                    }
                }

                if (notFound) {
                    if (matrix[i][numberEquations].equals(zero)) {
                        numberSolutions = NumberSolutions.MANY;
                        continue;
                    } else {
                        numberSolutions = NumberSolutions.NONE;
                        return;
                    }
                }
            }

            if (!matrix[i][i].equals(one)) {
                divideRow(i, matrix[i][i]);
            }
            for (int j = i + 1; j < numberEquations && j < numberVariables; ++j) {
                final Complex k = Complex.multiply(minusOne, matrix[j][i]);
                if (!k.equals(zero)) {
                    addKRow1ToRow2(k, i, j);
                }
            }
        }
    }

    private void gausSecondStep() {
        for (int i = numberVariables - 1; i >= 0; --i) {
            for (int j = i - 1; j >= 0; --j) {
                final Complex k = Complex.multiply(minusOne, matrix[j][i]);
                if (!k.equals(zero)) {
                    addKRow1ToRow2(k, i, j);
                }
            }
        }
    }

    private void generateSolutions() {
        for (int i = 0; i < numberEquations && i < numberVariables; ++i) {
            solutionParticular[solutionIndexes[i]] = matrix[i][numberVariables];
            if (matrix[i][i].equals(zero)) {
                solutionGeneral[solutionIndexes[i]] = "x" + (solutionIndexes[i] + 1);
            } else {
                solutionGeneral[solutionIndexes[i]] = matrix[i][numberVariables].toString();
                for (int j = i + 1; j < numberVariables; ++j) {
                    if (!matrix[i][j].equals(zero)) {
                        solutionGeneral[solutionIndexes[i]] = solutionGeneral[solutionIndexes[i]] + " - x" +
                                (solutionIndexes[j] + 1) + " * (" + matrix[i][j].toString() + ")";
                    }
                }
            }
        }
    }

    private void checkThatSolutionIsSane() {
        for (int i = numberVariables; i < numberEquations; ++i) {
            Complex sum = new Complex(0.0, 0.0);
            for (int j = 0; j < numberVariables; ++j) {
                Complex temp = Complex.multiply(solutionParticular[solutionIndexes[j]], matrix[i][solutionIndexes[j]]);
                sum = Complex.add(sum, temp);
            }
            if (!sum.equals(matrix[i][numberVariables])) {
                numberSolutions = NumberSolutions.NONE;
                return;
            }
        }
    }

    public void solve() {
        if (verbose) {
            System.out.println("Start solving the equation.");
            System.out.println("Rows manipulation:");
        }
        gausFirstStep();
        if (numberSolutions != NumberSolutions.NONE) {
            gausSecondStep();
            generateSolutions();
            checkThatSolutionIsSane();
        }
        printSolution();
    }

    private void printSolution() {
        if (verbose) {
            printSolutionInternal(new PrintWriter(System.out, true));
        }
    }

    private void printSolutionInternal(PrintWriter printWriter) {
        switch(numberSolutions) {
            case NONE:
                printWriter.println("There are no solutions");
                break;
            case ONE:
                printWriter.printf("(%s", solutionParticular[0].toString());
                for (int i = 1; i < solutionParticular.length; ++i) {
                    printWriter.printf(", %s", solutionParticular[i].toString());
                }
                printWriter.println(")");
                break;
            case MANY:
                printWriter.printf("(%s", solutionGeneral[0]);
                for (int i = 1; i < solutionParticular.length; ++i) {
                    printWriter.printf(", %s", solutionGeneral[i]);
                }
                printWriter.println(")");
                break;
            default:
                assert (false);
                break;
        }
        printWriter.flush();
    }

    private void swapColumns(int column1, int column2) {
        if (verbose) {
            System.out.printf("C%d <-> C%d\n", column1 + 1, column2 + 1);
        }
        final int n = matrix.length;
        for (int i = 0; i < n; ++i) {
            final Complex temp = matrix[i][column1];
            matrix[i][column1] = matrix[i][column2];
            matrix[i][column2] = temp;
        }
        final int temp = solutionIndexes[column1];
        solutionIndexes[column1] = solutionIndexes[column2];
        solutionIndexes[column2] = temp;
    }

    public void writeSolutionToFile(String out) throws FileNotFoundException {
        final File file = new File(out);
        final PrintWriter printWriter = new PrintWriter(file);
        printSolutionInternal(printWriter);
        printWriter.close();
        if (verbose) {
            System.out.printf("Saved to file %s", out);
        }
    }

    public Complex[] getSolutionParticular() {
        if (numberSolutions == NumberSolutions.NONE) {
            return null;
        }
        return solutionParticular;
    }

    public NumberSolutions getNumberSolutions() {
        return numberSolutions;
    }

    public String[] getSolutionGeneral() {
        if (numberSolutions != NumberSolutions.MANY) {
            return null;
        }
        return solutionGeneral;
    }
}
