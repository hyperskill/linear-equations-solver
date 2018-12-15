package GaussianElimintation;

import java.io.*;

/**
 * Обязательные аргументы запуска это флаги "-in" и "-out", после которых следует имя файла с исходными данными или
 * имя файла для вывода значения сооттветственно.
 */

public class Main {
    public static void main(String[] args) {
        BufferedReader in = null;
        BufferedWriter out = null;
        double[][] matrix;
        double[] result;
        String[] buffer;
        int N;
        GaussianElimintation gaussianElimintation;

        try {
            for (int counter = 0; counter < 4; counter++) {
                if (args[counter].equals("-in")) {
                    in = new BufferedReader(new FileReader(args[++counter]));
                }
                if (args[counter].equals("-out")) {
                    out = new BufferedWriter(new FileWriter(args[++counter]));
                }
            }

            if (in == null || out == null) return;

            buffer = in.readLine().split(" ");
            N = Integer.parseInt(buffer[0]);
            matrix = new double[N][];

            for (int row = 0; row < N; row++) {
                matrix[row] = new double[N + 1];
                buffer = in.readLine().split(" ");
                for (int column = 0; column <= N; column++) {
                    matrix[row][column] = Double.parseDouble(buffer[column]);
                }
            }
            in.close();
            gaussianElimintation = new GaussianElimintation(matrix);
            result = gaussianElimintation.findAnswer();
            for (int counter = 0; counter < N; counter++) {
                out.append(Double.toString(result[counter]) + " ");
            }
            out.close();
            System.out.println("Completed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
