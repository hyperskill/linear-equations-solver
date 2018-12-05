package solver;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by DIMA, on 03.12.2018
 */
class Solver {

    private double[][] calcGaussian(double[][] matrix){
        // 1*x1
        if(matrix[0][0] != 1){
            divideRowToOne(0, matrix[0][0], matrix);
        }
        for(int row = 1; row < matrix.length; row++){
            //nulling columns [0, row)
            for(int column = 0; column < row; column++){
                double multifactorXi = matrix[row][column];
                //  -multiFactorXi * matrix[column] + matrix[row]
                for(int k = 0; k < matrix[row].length; k++){
                    double oldValue = matrix[column][k];
                    double newValue = -oldValue*multifactorXi + matrix[row][k];
                    matrix[row][k] = newValue;
                }
            }
            //1*xi
            if(matrix[row][row] != 1){
                divideRowToOne(row, matrix[row][row], matrix);
            }
        }
        return matrix;
    }

    double[][] calcGausJordan(double[][] matrix){
        double[][] matrixGausJordan = calcGaussian(matrix);
        //from row last but one
        for (int row = matrixGausJordan.length - 2; row >= 0; row--){
            //above main diagonal
            // 1 a b c
            // 0 1 d f
            // 0 0 1 k
            for (int column = row + 1; column < matrixGausJordan.length; column++){
                double multifactorXi = matrixGausJordan[row][column];
                for(int k = 0; k < matrixGausJordan[row].length; k++){
                    double oldValue = matrixGausJordan[column][k];
                    double newValue = -oldValue*multifactorXi + matrixGausJordan[row][k];
                    matrixGausJordan[row][k] = newValue;
                }
            }
        }
        return matrixGausJordan;
    }


    private void divideRowToOne(int row, double devide, double[][] matrix){
        for(int column = 0; column < matrix[row].length; column++){
            double item = matrix[row][column];
            matrix[row][column] = item/devide;
        }
    }

    void print(double matrix[][]){
        String s = Arrays.stream(matrix)
                .map(i -> Arrays.stream(i).boxed()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));

        System.out.println(s);
    }



}
