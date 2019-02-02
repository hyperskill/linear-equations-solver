package solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double[][] matrix;
        double[]   solution;

        matrix = readMatrix(args[1]);
        System.out.println("Original matrix: ");
        printMatrix(matrix);

        matrix = goDown(matrix);
        System.out.println("Triangular matrix: ");
        printMatrix(matrix);

        matrix = goUp(matrix);
        System.out.println("Reduced matrix: ");
        printMatrix(matrix);

        solution = findSolution(matrix);
        System.out.println("Solution: ");
        System.out.println(Arrays.toString(solution));
        
        writeSolution(args[3], solution);
    }

    private static void writeSolution(String out, double[] solution) {
        File file = new File(out);

        try {
            FileWriter writer = new FileWriter(file);
            for(Double d : solution){
                writer.write(d + " ");
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static double[] findSolution(double[][] matrix) {
        double[] answer = new double[matrix.length];
        for(int i = 0; i < answer.length; ++i){
            answer[i] = matrix[i][matrix[0].length-1] / matrix[i][i];
        }

        return answer;
    }

    private static double[][] goUp(double[][] matrix) {
        for(int masterRow = matrix.length-1; masterRow > 0; --masterRow){
            for(int currentRow = masterRow - 1; currentRow >=0; --currentRow){
                double multiplier = matrix[currentRow][masterRow] / matrix[masterRow][masterRow];
                for(int element = masterRow; element < matrix[0].length; element++){
                    matrix[currentRow][element] -= (matrix[masterRow][element] * multiplier);
                }
            }
        }
        return matrix;
    }

    private static double[][] goDown(double[][] matrix) {
        for(int masterRow = 0; masterRow < matrix.length - 1; ++masterRow){
            for(int currentRow = masterRow + 1; currentRow < matrix.length; ++currentRow){
                double multiplier = matrix[currentRow][masterRow] / matrix[masterRow][masterRow];
                for(int element = masterRow; element < matrix[0].length; ++element){
                    matrix[currentRow][element] -= (matrix[masterRow][element] * multiplier);
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