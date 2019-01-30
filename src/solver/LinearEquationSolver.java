package solver;

public class LinearEquationSolver {
    AugmentedMatrix matrix;

    LinearEquationSolver(AugmentedMatrix matrix) {
        this.matrix = new AugmentedMatrix(matrix.getMatrix());
    }

    public void transformToUpperTriangularForm() {
        Row currentRow;
        int n = matrix.size()[0];
        for(int i = 0; i < n; i++) {
            currentRow = matrix.getRow(i).normalizeRow(i);
            for(int j = i + 1; j < n; j++) {
                matrix.getRow(j).normalizeRow(i).subtract(currentRow);
            }
        }
    }

    public void transformToLowerTriangularForm() {
        Row currentRow;
        int n = matrix.size()[0];
        for(int i = n-1; i >= 0; i--) {
            currentRow = matrix.getRow(i).normalizeRow(i);
            for(int j = i-1; j >= 0; j--) {
                matrix.getRow(j).normalizeRow(i).subtract(currentRow);
            }
        }
    }

    public void tranformToNormalizedDiagonalForm() {
        transformToUpperTriangularForm();
        transformToLowerTriangularForm();
    }

    public AugmentedMatrix getMatrix() {
        return matrix;
    }
}
