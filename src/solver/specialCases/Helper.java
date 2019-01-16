package solver.specialCases;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by DIMA, on 14.01.2019
 */
public class Helper {
    public static void print(double matrix[][]){
        System.out.println("Start print");
        String s = Arrays.stream(matrix)
                .map(i -> Arrays.stream(i).boxed()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));

        System.out.println(s);
        System.out.println("End print");
    }

    public static void roundInMatrix(double[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if(Math.abs(matrix[i][j]) < Checker.DELTA_DOUBLE){
                    matrix[i][j] = 0;
                }
            }
        }
    }
}
