package solver.specialCases;

import solver.Complex;

import java.util.Deque;

/**
 * Created by DIMA, on 07.12.2018
 */
public class Swapper {

    public static void swapColumns (int oldColumns, int newColumns, Complex[][] matrix){
        for (int row = 0; row < matrix.length; row++){
            Complex tmp = matrix[row][newColumns];
            matrix[row][newColumns] = matrix[row][oldColumns];
            matrix[row][oldColumns] = tmp;
        }
    }

    public static void swapRows(int oldRow, int newRow, Complex[][] matrix){
        for(int column = 0; column < matrix[oldRow].length; column++){
            Complex tmp = matrix[newRow][column];
            matrix[newRow][column] = matrix[oldRow][column];
            matrix[oldRow][column] = tmp;
        }
    }

    private static void unSwapColumns(Swap swap, Complex[][] matrix){
        int oldColumn = swap.getNewColumnsNumber();
        int newColumn = swap.getOldColumnsNumber();
        swapColumns (oldColumn, newColumn, matrix);
    }

    public static void unSwapAllC(Deque<Swap> swapDeque, Complex[][] matrix){
        Swap swap;
        while ((swap = swapDeque.pollLast()) != null){
            unSwapColumns(swap, matrix);
        }
    }

}
