package solver;

import solver.specialCases.*;

/**
 * Created by DIMA, on 03.12.2018
 */
class Solver {
    boolean noSolutions = false;
    boolean infinitySolutions = false;
    double[][] matrix;

    public Solver(Matrix m){
        matrix = m.getMatrix();
    }


    private double[][] calcGaussian(double[][] matrix){
        // 1*x1
        int matrixSize = matrix.length;
        for (int row = 0; row < matrixSize; row++){
            if(!Checker.isAllZero(row, matrix)){
                if(!Checker.hasMainNumber(row, matrix)){
                    int findedRow = Searcher.searchRowDown(row,matrix);
                    if(findedRow != -1){
                        Swapper.swapRows(row, findedRow, matrix);
                    }else {
                        int findedColumn = Searcher.searchColumnRight(row, matrix);
                        if (findedColumn != -1){
                            Swapper.swapColumns(row, findedColumn, matrix);
                            SwapStore.addSwap(row, findedColumn);
                        }else {
                            if(!Checker.isZeroInRightColumn(row, matrix)){
                                noSolutions = true;
                                return null;
                            }else {
                                continue;
                            }
                        }
                    }
                }
            }else {
                matrix = deleteRow(row, matrix);
                row--;
                matrixSize--;
                continue;
            }
            if(row != 0){
                nullingRowGauss(row, matrix);
            }
            if(!Checker.isAllZero(row, matrix)){
                divideRowToOne(row, matrix[row][row], matrix);
            }
        }
        if(Checker.checkToNoSolution(matrix)){
            noSolutions = true;
            return null;
        }
        //Helper.print(matrix);
        Swapper.unSwapAllC(SwapStore.swapStack, matrix);
        matrix = deleteZeroRows(matrix);
        if(Checker.checkToNoSolution(matrix)){
            noSolutions = true;
            return null;
        }
        return matrix;
    }

    private void nullingRowGauss(int row, double[][] matrix){
        for(int column = 0; column < row; column++){
            double multifactorXi = matrix[row][column];
            //  -multiFactorXi * matrix[column] + matrix[row]
            for(int k = 0; k < matrix[row].length; k++){
                double oldValue = matrix[column][k];
                double newValue = -oldValue*multifactorXi + matrix[row][k];
                matrix[row][k] = newValue;
            }
        }
    }

    double[][] calcGaussJordan(double[][] matrix){
        double[][] matrixGausJordan = calcGaussian(matrix);
        if(noSolutions || matrixGausJordan == null) return null;
        //from row last but one
        for (int row = matrixGausJordan.length - 2; row >= 0; row--){
            //above main diagonal
            // 1 a b c
            // 0 1 d f
            // 0 0 1 k
            for (int column = row + 1; column < matrixGausJordan.length; column++){
                double multifactorXi = matrixGausJordan[row][column];
                for(int k = 0; k < matrixGausJordan[row].length; k++){
                    double oldValue = matrixGausJordan[column][k];
                    double newValue = -oldValue*multifactorXi + matrixGausJordan[row][k];
                    matrixGausJordan[row][k] = newValue;
                }
            }
        }
        if(matrixGausJordan.length < matrixGausJordan[0].length - 1){
            infinitySolutions = true;
        }
        //Helper.print(matrixGausJordan);
        return matrixGausJordan;
    }

    private void divideRowToOne(int row, double devide, double[][] matrix){
        //if(row >= matrix[0].lenght) do nothing
        if(row < matrix[0].length){
            for(int column = 0; column < matrix[row].length; column++){
                double item = matrix[row][column];
                matrix[row][column] = item/devide;
            }
        }
    }

    private double[][] deleteZeroRows(double[][] matrix){
        int matrixSize = matrix.length;
        for(int row = 0; row < matrixSize; row++){
            if(Checker.isAllZero(row, matrix)){
                //del row
                matrix = deleteRow(row, matrix);
                row--;
                matrixSize--;
            }
        }
        return matrix;
    }

    private static double[][] deleteRow (int row, double[][] matrix){
        double[][] trimMatrix = new double[matrix.length-1][matrix[row].length];
        for(int i = 0; i < row; i++){
            for (int j = 0; j < matrix[i].length; j ++){
                trimMatrix[i][j] = matrix[i][j];
            }
        }
        for(int i = row+1; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                trimMatrix[i-1][j] = matrix[i][j];
            }
        }
        return trimMatrix;
    }
}
