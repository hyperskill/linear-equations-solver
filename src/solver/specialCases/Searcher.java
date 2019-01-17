package solver.specialCases;

import solver.Complex;

/**
 * Created by DIMA, on 10.12.2018
 */
public class Searcher {
    public static int searchRowDown(int startRow, Complex[][] matrix){
        for(int i = startRow + 1; i < matrix.length; i ++){
            if(Math.abs(matrix[i][startRow].getRealPart()) > Checker.DELTA_DOUBLE || Math.abs(matrix[i][startRow].getImaginaryPart()) > Checker.DELTA_DOUBLE){
                return i;
            }
        }
        return -1;
    }

    public static int searchColumnRight(int row, Complex[][] matrix){
        for(int column = row + 1; column < matrix[row].length - 1; column++){
            if(Math.abs(matrix[row][column].getRealPart()) > Checker.DELTA_DOUBLE || Math.abs(matrix[row][column].getImaginaryPart()) > Checker.DELTA_DOUBLE){
                return column;
            }
        }
        return -1;
    }
}
