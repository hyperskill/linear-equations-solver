package GaussianElimintation;

/**
 * Класс для нахлждения вектора решений системы уравнений методом Гаусса
 */

public class GaussianElimintation {
    private Matrix matrix;
    private double result[];
    private int N;

    public GaussianElimintation(double[][] matrix) {
        setMatrix(matrix);
    }

    public GaussianElimintation(Double[][] matrix) {
        setMatrix(matrix);
    }

    public GaussianElimintation(Matrix matrix) {
        setMatrix(matrix);
    }

    public double[] findAnswer() {
        double coefficient;
        result = new double[N--];
        for (int counter = 0; counter < N; counter++) {
            for (int next = counter + 1; next <= N; next++) {
                coefficient = matrix.getElement(next, counter) / matrix.getElement(counter, counter) * -1;
                matrix.addRow(coefficient, counter, next, counter);
            }
        }
        for (int counter = N; counter >= 0; counter--) {
            result[counter] = (matrix.getElement(counter, N + 1) - calculate(counter)) / matrix.getElement(counter, counter);
        }
        return result;
    }

    /**
     * Проверяет является ли матрица квадратной. Вектор значение после знака равенства не учитывается.
     *
     * @param matrix Матрица для проверки, включая столбец значений после знака раменства.
     * @return true - матрица квадратная, false - нет
     */
    public static boolean isSquareMatrix(Matrix matrix) {
        boolean result = true;
        int N = matrix.getN();
        for (Row row : matrix.getRows()) {
            result &= row.getDoubleList().size() == N + 1;
        }
        return result;
    }

    /**
     * Вычисление известной части уравнения
     * @param stage индекс после которого происходит расчёт
     * @return сумма известных частей полинома
     */
    private double calculate(int stage) {
        double result = 0.0;
        if (stage != N) {
            for (int counter = stage + 1; counter <= N; counter++) {
                result += matrix.getElement(stage, counter) * this.result[counter];
            }
        }
        return result;
    }

    public void setMatrix(double[][] matrix) {
        setMatrix(new Matrix(matrix));
    }

    public void setMatrix(Double[][] matrix) {
        setMatrix(new Matrix(matrix));
    }

    public void setMatrix(Matrix matrix) {
        try {
            if (!isSquareMatrix(matrix)) throw new RuntimeException("This matrix is not square matrix.");
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
        this.matrix = matrix;
        N = this.matrix.getN();
        result = null;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public double[] getResult() {
        if (result == null) result = findAnswer();
        return result;
    }
}
