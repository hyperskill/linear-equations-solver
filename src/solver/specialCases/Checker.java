package solver.specialCases;

/**
 * Created by DIMA, on 07.12.2018
 */
public class Checker {
    public static final double DELTA_DOUBLE = 0.000001;

    public static boolean hasZeroCorrespondingElement(int row, double[][] matrix){
        if (matrix[row].length < row) return false;
        return Math.abs(matrix[row][row]) < DELTA_DOUBLE;
    }

    public static boolean checkRowToNoSolution(int row, double[][] matrix){
       for(int column = 0; column < matrix[row].length - 1; column ++){
            if(Math.abs(matrix[row][column]) > DELTA_DOUBLE){
                return false;
            }
        }
        // if no return that means all elements is zero
        return Math.abs(matrix[row][matrix[row].length - 1]) > DELTA_DOUBLE;
    }
}
