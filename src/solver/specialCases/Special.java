package solver.specialCases;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Created by DIMA, on 06.12.2018
 */
public class Special {
    public static Deque<Swap> swapStack = new ArrayDeque<>();

    public void addSwap(int oldColumn, int newColumn){
        Swap swap = new Swap(oldColumn, newColumn);
        swapStack.addLast(swap);
    }

    public static double[][] deleteRow (int row, double[][] matrix){
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

    public double[][] transformMatrix(int row, double[][] matrix){
        if(matrix[row][row] == 0){
            int newRow = Searcher.searchRowDown(0, 0, matrix);
            if(newRow == -1){
                newRow = Searcher.searchColumnRight(0,0,matrix);
                if(newRow == -1){
                    if(matrix[row][matrix[row].length-1] != 0){
                        // no solution
                    }else {
                        Special.deleteRow(0, matrix);
                    }
                }
            }
        }
        return null;



    }


    public static void print(double matrix[][]){
        String s = Arrays.stream(matrix)
                .map(i -> Arrays.stream(i).boxed()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));

        System.out.println(s);
    }


}
