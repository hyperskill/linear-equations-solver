package solver;

import java.io.File;

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
            m.print();
            System.out.println(m.rows[1].transform1(0));
            //System.out.println(m.rows[1].asString());
            m.print();
            System.out.println(m.rows[1].transform0(0, m.rows[0]));
            m.print();

        }
        catch (IOException e){
            System.out.println("File reading error: " + inFile);
        }
        catch (InvalidDataFileFormat | ParsingArrayException e){
            System.out.println(e.getMessage());
        }
    }
}