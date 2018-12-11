package GaussianElimintation;

public class GaussianElimintation {
    private double matrix[][];
    private double result[];
    private int N;

    public GaussianElimintation(double[][] matrix) {
        setMatrix(matrix);
    }

    private double[] findAnswer() {
        double coefficient;
        result = new double[N--];
        for (int counter = 0; counter < N; counter++) {
            for (int next = counter + 1; next <= N; next++) {
                coefficient = matrix[next][counter] / matrix[counter][counter] * -1;
                add(coefficient, matrix[counter], matrix[next], counter);
            }
        }
        for (int counter = N; counter >= 0; counter--) {
            result[counter] = (matrix[counter][N + 1] - calculate(counter)) / matrix[counter][counter];
        }
        return result;
    }

    private double[] add(double[] what, double[] to, int skipFirst) {
        return add(1, what, to, skipFirst);
    }

    private double[] add(double[] what, double[] to) {
        return add(1, what, to, 0);
    }

    private double[] add(double coefficient, double[] what, double[] to, int skipFirst) {
        for (int counter = skipFirst; counter <= N + 1; counter++) {
            to[counter] += what[counter] * coefficient;
        }
        return to;
    }

    public static boolean isSquareMatrix(double[][] matrix) {
        boolean result = true;
        int N = matrix.length;
        for (double[] line : matrix) {
            result &= line.length == N + 1;
        }
        return result;
    }

    private double calculate(int stage) {
        double result = 0.0;
        if (stage != N) {
            for (int counter = stage + 1; counter <= N; counter++) {
                result += matrix[stage][counter] * this.result[counter];
            }
        }
        return result;
    }

    public void setMatrix(double[][] matrix) {
        try {
            if (!isSquareMatrix(matrix)) throw new RuntimeException("This matrix is not square matrix.");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
        this.matrix = matrix;
        N = this.matrix.length;
        result = null;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double[] getResult() {
        if (result == null) result = findAnswer();
        return result;
    }
}
