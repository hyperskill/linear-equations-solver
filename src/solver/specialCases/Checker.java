package solver.specialCases;

/**
 * Created by DIMA, on 07.12.2018
 */
public class Checker {
    static final double DELTA_DOUBLE = 0.000001;

    public static boolean hasMainNumber(int row, double[][] matrix){
        if (row >= matrix[row].length) return true;
        return Math.abs(matrix[row][row]) > DELTA_DOUBLE;
    }

    public static boolean isZeroInRightColumn(int row, double[][] matrix){
        return Math.abs(matrix[row][matrix[row].length - 1]) > DELTA_DOUBLE;
    }

    public static boolean isAllZero(int row, double[][] matrix){
        for (int column = 0; column < matrix[row].length; column++){
            if(matrix[row][column] > DELTA_DOUBLE) return false;
        }
        return true;
    }

    public static boolean checkToNoSolution(double[][] matrix){
       for(int row = 0; row < matrix.length; row++){
           boolean zeroInLeftSide = true;
           //check left side
           for(int column = 0; column < matrix[0].length - 1; column ++){
               if(Math.abs(matrix[row][column]) > DELTA_DOUBLE){
                   zeroInLeftSide = false;
               }
           }
           if(zeroInLeftSide){
               if(Math.abs(matrix[row][matrix[row].length - 1]) > DELTA_DOUBLE){
                   return true;
               }
           }
       }
       return false;
    }
}
