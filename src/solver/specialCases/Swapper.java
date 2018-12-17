package solver.specialCases;

import java.util.Deque;

/**
 * Created by DIMA, on 07.12.2018
 */
public class Swapper {

    public static void swapColumns (int oldColumns, int newColumns, double[][] matrix){
        for (int row = 0; row < matrix.length; row++){
            double tmp = matrix[row][newColumns];
            matrix[row][newColumns] = matrix[row][oldColumns];
            matrix[row][oldColumns] = tmp;
        }
    }

    public static void swapRows(int oldRow, int newRow, double[][] matrix){
        for(int column = 0; column < matrix[oldRow].length; column++){
            double tmp = matrix[newRow][column];
            matrix[newRow][column] = matrix[oldRow][column];
            matrix[oldRow][column] = tmp;
        }
    }

    private static void unSwapColumns(Swap swap, double[][] matrix){
        int sourceColumn = swap.getNewColumnsNumber();
        int targetColumn = swap.getOldColumnsNumber();
        for(int row = 0; row < matrix.length; row++){
            double tmp = matrix[row][sourceColumn];
            matrix[row][sourceColumn] = matrix[row][targetColumn];
            matrix[row][targetColumn] = tmp;
        }
    }

    public static void unSwapAllC(Deque<Swap> swapDeque, double[][] matrix){
        Swap swap;
        while ((swap = swapDeque.pollLast()) != null){
            unSwapColumns(swap, matrix);
        }
    }

}
