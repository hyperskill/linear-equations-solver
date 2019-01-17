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
    private Complex[][] matrix;

    Matrix(String fileName){
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))){
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();
            matrix = new Complex[rows][columns];
            for(int i = 0; i < rows; i++){
                for (int j = 0; j < columns; j++){
                    String item = scanner.next();

                    matrix[i][j] = new Complex(item);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Please check file of coefficients");
            System.out.println(e.getMessage());
        }
    }

/*    @Override
    public String toString() {
        return Arrays.stream(matrix)
                .map(i -> Arrays.stream(i).boxed()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }*/

    public Complex[][] getMatrix() {
        return matrix;
    }
}
