package solver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by DIMA, on 03.12.2018
 */
public class Matrix {
    private double[][] matrix;

    Matrix(String fileName){
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))){
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();
            matrix = new double[rows][columns];
            for(int i = 0; i < rows; i++){
                for (int j = 0; j < columns; j++){
                    matrix[i][j] = scanner.nextDouble();
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Please check file of coefficients");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(matrix)
                .map(i -> Arrays.stream(i).boxed()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
