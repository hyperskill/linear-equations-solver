package solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double[][] matrix;

        matrix = readMatrix(args[1]);
        printMatrix(matrix);

        matrix = goDown(matrix);
        printMatrix(matrix);
    }

    private static double[][] goDown(double[][] matrix) {
        for(int masterRow = 0; masterRow < matrix.length - 1; ++masterRow){
            for(int currentRow = masterRow + 1; currentRow < matrix.length; ++currentRow){
                for(int element = masterRow; element < matrix[0].length; ++element){
                    matrix[currentRow][element] -= (matrix[masterRow][element] * matrix[currentRow][masterRow] / matrix[masterRow][masterRow]);
                }
            }
        }
        return matrix;
    }

    static double[][] readMatrix(String in){
        int rows, columns;
        double[][] matrix;

        File file = new File(in);

        try {
            Scanner scanner = new Scanner(file);

            rows = scanner.nextInt();
            columns = rows + 1;

            matrix = new double[rows][columns];

            for(int i = 0; i < columns * rows; ++i){
                matrix[i/columns][i%columns] = scanner.nextInt();
            }

            return matrix;
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }

    static void printMatrix(double[][] matrix){
        for(double[] row : matrix){
            for(Double element : row){
                System.out.print(element + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}