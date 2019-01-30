package solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static String showHelp() {
        String helpMsg = "This program is intended to solve Linear Equations with any amount of variables. \n\n" +
                "Options: " +
                "\n\t * -in [pathToFile] reads file with matrix coefficients and constant column of numbers " +
                "\n\t * -out [pathToFile] writes results to the output file " +
                "\n\t * -h shows help information " +
                "\n\nExample: \njava Solver -in in.txt -out out.txt";
        return helpMsg;
    }

    public static void main(String[] args) {
        // Getting and handling console parameters
        String inputFile="";
        String outputFile="";
        for(int i = 0; i < args.length; i++) {
            try {
                switch (args[i]) {
                    case "-in":
                        inputFile = args[i + 1];
                        break;
                    case "-out":
                        outputFile = args[i + 1];
                        break;
                    case "-h" :
                        System.out.println(showHelp());
                        return;

                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Invalid command line options, use '-h' option to see help");
                return;
            }
        }

        if(inputFile.isEmpty() || outputFile.isEmpty()) {
            System.err.println("Invalid command line options, use '-h' option to see help");
            return;
        }

        System.out.println("Input File: " + inputFile + "\nOutput File: " + outputFile);

        // Working with input file
        File file = new File(inputFile);
        AugmentedMatrix matrix = new AugmentedMatrix();
        try {
            Scanner fileScanner = new Scanner(file);
            matrix.readMatrix(fileScanner);
        } catch(FileNotFoundException e) {
            System.out.println("File: " + file.getAbsolutePath() + " doesn't exist");
            return;
        } catch (InputMismatchException e) {
            System.err.println("Invalid structure of input file: " + file.getAbsolutePath());
            return;
        }

        System.out.println(matrix);

//        for(int i = 0; i < matrix.size()[0]; i++) {
//            matrix.getRow(i).normalizeRow(0);
//        }
//
//        matrix.getRow(1).subtract(matrix.getRow(0));
//        matrix.getRow(2).subtract(matrix.getRow(0));

        LinearEquationSolver solver = new LinearEquationSolver(matrix);
        solver.transformToUpperTriangularForm();
        solver.transformToLowerTriangularForm();

        System.out.println();
        System.out.println(solver.getMatrix());

    }
}