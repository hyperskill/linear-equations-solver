package solver;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    static String inFile;
    static String outFile;
    public static void main(String[] args) throws InvalidDataFileFormat {
        //get_in_file
        if (args.length!=4) {
            System.out.println("Invalid comand-line arguments");
            return;
        }

        for (int i = 0; i < args.length; i+=2) {
            if (args[i].equalsIgnoreCase("-in"))
                inFile = args[i+1];
            else if (args[i].equalsIgnoreCase("-out"))
                outFile = args[i+1];
        }
        try {
            List<String> lines = Files.readAllLines( Paths.get(inFile));
            int n;
            try {
                 n = Integer.parseInt(lines.get(0));
            }
            catch (NumberFormatException e){
                throw new InvalidDataFileFormat(
                        //String.format("The string '%s' cannot be parsed as an array of numbers", s),
                        String.format("Incorrect data file format, '%s' cannot be parsed as number", lines.get(0)),
                        e);
            }
            lines.remove(0);
            if (lines.size() != n) {
                throw new InvalidDataFileFormat("Incorrect data file format", null);
            }
            System.out.println("Start solving the equation.\n" +
                    "Rows manipulation:");
            Matrix m = new Matrix(n);
            for (String line : lines) {
                m.addRow(lines.indexOf(line), line);
            }
            //m.print();

            for (int i = 0; i < m.N; i++) {
                System.out.println(m.rows[i].transform1(i));
                //m.print();
                for (int j = i+1; j < m.N; j++) {
                    System.out.println(m.rows[j].transform0(i, m.rows[i]));
                    //m.print();
                }
            }
            //System.out.println("-------");
            for (int i = m.N-1; i >= 0; i--) {
                for (int j = i-1; j >= 0; j--) {
                    System.out.println(m.rows[j].transform0(i, m.rows[i]));
                    //m.print();
                }
            }
            String result = m.printSolution();

            File file = new File(outFile);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(result);
                System.out.printf("Saved to file %s\n",outFile);
            } catch (IOException e) {
                System.out.printf("File writing error %s", e.getMessage());
            }
        }
        catch (IOException e){
            System.out.println("File reading error: " + inFile);
        }
        catch (InvalidDataFileFormat | ParsingArrayException e){
            System.out.println(e.getMessage());
        }
    }
}