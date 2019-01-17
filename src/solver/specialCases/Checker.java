package solver.specialCases;

import solver.Complex;

/**
 * Created by DIMA, on 07.12.2018
 */
public class Checker {
    static final double DELTA_DOUBLE = 0.000001;

    public static boolean hasMainNumber(int row, Complex[][] matrix){
        if (row >= matrix[row].length) return true;
        return Math.abs(matrix[row][row].getRealPart()) > DELTA_DOUBLE || Math.abs(matrix[row][row].getImaginaryPart()) > DELTA_DOUBLE;
    }

    public static boolean isZeroInRightColumn(int row, Complex[][] matrix){
        return Math.abs(matrix[row][matrix[row].length - 1].getRealPart()) > DELTA_DOUBLE || Math.abs(matrix[row][matrix[row].length - 1].getImaginaryPart()) > DELTA_DOUBLE;
    }

    public static boolean isAllZero(int row, Complex[][] matrix){
        for (int column = 0; column < matrix[row].length; column++){
            if(Math.abs(matrix[row][column].getRealPart()) > DELTA_DOUBLE || Math.abs(matrix[row][column].getImaginaryPart()) > DELTA_DOUBLE) return false;
        }
        return true;
    }

    public static boolean checkToNoSolution(Complex[][] matrix){
       for(int row = 0; row < matrix.length; row++){
           boolean zeroInLeftSide = true;
           //check left side
           for(int column = 0; column < matrix[0].length - 1; column ++){
               if(Math.abs(matrix[row][column].getRealPart()) > DELTA_DOUBLE || Math.abs(matrix[row][column].getImaginaryPart()) > DELTA_DOUBLE){
                   zeroInLeftSide = false;
               }
           }
           if(zeroInLeftSide){
               if(Math.abs(matrix[row][matrix[row].length - 1].getRealPart()) > DELTA_DOUBLE || Math.abs(matrix[row][matrix[row].length - 1].getImaginaryPart()) > DELTA_DOUBLE){
                   return true;
               }
           }
       }
       return false;
    }
}
