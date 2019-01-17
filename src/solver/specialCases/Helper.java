package solver.specialCases;

import solver.Complex;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by DIMA, on 14.01.2019
 */
public class Helper {
    public static void print(Complex matrix[][]){
        System.out.println("Start print");
        StringBuilder sb = new StringBuilder();
        for(int row = 0; row < matrix.length; row++){
            for(int column = 0; column < matrix[row].length; column++){
                sb.append(matrix[row][column].getRealPart());
                double imaginary = matrix[row][column].getImaginaryPart();
                if(imaginary > 0){
                    sb.append("+");
                }
                if(imaginary == 0){
                    sb.append(" ");
                }else {
                    sb.append(imaginary).append("i");
                    sb.append(" ");
                }

            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        System.out.println("End print");
    }

}
