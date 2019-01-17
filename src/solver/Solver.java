package solver;

import solver.specialCases.*;

/**
 * Created by DIMA, on 03.12.2018
 */
class Solver {
    boolean noSolutions = false;
    boolean infinitySolutions = false;
    Complex[][] matrix;

    public Solver(Matrix m){
        matrix = m.getMatrix();
    }


    private Complex[][] calcGaussian(Complex[][] matrix){
        Complex[][] gaussMatrix = matrix;
        // 1*x1
        int matrixSize = gaussMatrix.length;
        for (int row = 0; row < matrixSize; row++){
            if(!Checker.isAllZero(row, gaussMatrix)){
                if(!Checker.hasMainNumber(row, gaussMatrix)){
                    int findedRow = Searcher.searchRowDown(row, gaussMatrix);
                    if(findedRow != -1){
                        Swapper.swapRows(row, findedRow, gaussMatrix);
                    }else {
                        int findedColumn = Searcher.searchColumnRight(row, gaussMatrix);
                        if (findedColumn != -1){
                            Swapper.swapColumns(row, findedColumn, gaussMatrix);
                            SwapStore.addSwap(row, findedColumn);
                        }else {
                            if(!Checker.isZeroInRightColumn(row, gaussMatrix)){
                                noSolutions = true;
                                return null;
                            }else {
                                continue;
                            }
                        }
                    }
                }
            }else {
                gaussMatrix = deleteRow(row, gaussMatrix);
                row--;
                matrixSize--;
                continue;
            }
            if(row != 0){
                gaussMatrix = nullingRowGauss(row, gaussMatrix);
            }
            if(!Checker.isAllZero(row, matrix)){
                gaussMatrix = divideRowToOne(row, gaussMatrix[row][row], gaussMatrix);
            }
        }
        if(Checker.checkToNoSolution(gaussMatrix)){
            noSolutions = true;
            return null;
        }
        Swapper.unSwapAllC(SwapStore.swapStack, gaussMatrix);
        gaussMatrix = deleteZeroRows(gaussMatrix);
        if(Checker.checkToNoSolution(gaussMatrix)){
            noSolutions = true;
            return null;
        }
        return gaussMatrix;
    }

    Complex[][] calcGaussJordan(Complex[][] matrix){
        Complex[][] matrixGausJordan = calcGaussian(matrix);
        if(noSolutions || matrixGausJordan == null) return null;
        //from row last but one
        for (int row = matrixGausJordan.length - 2; row >= 0; row--){
            //above main diagonal
            // 1 a b c
            // 0 1 d f
            // 0 0 1 k
            for (int column = row + 1; column < matrixGausJordan.length; column++){
                Complex multy = matrixGausJordan[row][column];
                for(int k = 0; k < matrixGausJordan[row].length; k++){
                    Complex old = matrixGausJordan[column][k];
                    Complex newItem = old.changeSign().multiply(multy).adding(matrixGausJordan[row][k]);
                    matrixGausJordan[row][k] = newItem;
                }
            }
        }
        if(matrixGausJordan.length < matrixGausJordan[0].length - 1){
            infinitySolutions = true;
        }
        //Helper.print(matrixGausJordan);
        return matrixGausJordan;
    }

    private Complex[][] divideRowToOne(int row, Complex divider, Complex[][] matrix){
        Complex[][] dividedMatrix = matrix;
        //if(row >= matrix[0].lenght) do nothing
        if(row < dividedMatrix[0].length){
            for(int column = 0; column < dividedMatrix[row].length; column++){
                Complex item = dividedMatrix[row][column];
                Complex newItem = item.divide(divider);
                dividedMatrix[row][column] = newItem;
            }
        }
        return dividedMatrix;
    }

    private Complex[][] nullingRowGauss(int row, Complex[][] matrix){

        for(int column = 0; column < row; column++){
            Complex multy = matrix[row][column];
            for(int k = 0; k < matrix[row].length; k++){
                Complex old = matrix[column][k];
                Complex newItem = old.changeSign().multiply(multy).adding(matrix[row][k]);
                matrix[row][k] = newItem;
            }
        }
        return matrix;
    }

    private Complex[][] deleteZeroRows(Complex[][] matrix){
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

    private Complex[][] deleteRow (int row, Complex[][] matrix){
        Complex[][] trimMatrix = new Complex[matrix.length-1][matrix[row].length];
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
