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
            int n=0,v=0;
            int k=0;
            try {
                for (String s:lines.get(0).split("\\s")) {
                    if (k==0)
                        n = Integer.parseInt(s);//количество строк - уравнений
                    else
                        v = Integer.parseInt(s);//количество столбцов - переменных
                    k++;
                }
                if (v==0) v=n;
            }
            catch (NumberFormatException e){
                throw new InvalidDataFileFormat(
                        //String.format("The string '%s' cannot be parsed as an array of numbers", s),
                        String.format("Incorrect data file format, '%s' cannot be parsed as two numbers", lines.get(0)),
                        e);
            }
            lines.remove(0);
            if (lines.size() != n) {
                throw new InvalidDataFileFormat("Incorrect data file format, count of data rows isn't equal n", null);
            }
            System.out.println("Start solving the equation.\n" +
                    "Rows manipulation:");
            Matrix m;
            if (lines.contains("i"))
                m = new iMatrix(n,v);
            else
                m = new Matrix(n,v);

            for (String line : lines) {
                m.addRow(lines.indexOf(line), line);
            }
            String manipulation;
            //m.otladka = true;
            m.print();
            for (int i = 0; i < m.M; i++) {
                while (true)
                {
                    manipulation = m.rows[i].transform1(i);
                    if (!manipulation.equals("")) {
                        System.out.println(manipulation);
                        m.print();
                        if (!manipulation.contains("<->")) break;
                    } else break;
                };

                for (int j = i+1; j < m.N; j++) {
                    manipulation = m.rows[j].transform0(i, m.rows[i]);
                    if (!manipulation.equals("")) {
                        System.out.println(manipulation);
                        m.print();
                    }
                }
            }
            int check = m.checkSolution();

            String result="";
            switch (check) {
                case 0: {
                    result = "Infinite solutions";
                    System.out.println(result);
                    break;
                }
                case -1: {
                    result = "No solutions";
                    System.out.println(result);
                    break;
                }
                case 1: {
                    System.out.println("-------");
                    for (int i = m.N - 1; i >= 0; i--) {
                        for (int j = i - 1; j >= 0; j--) {
                            manipulation = m.rows[j].transform0(i, m.rows[i]);
                            if (!manipulation.equals("")) {
                                System.out.println(manipulation);
                                m.print();
                            }
                        }
                    }
                    result = m.printSolution();
                    break;
                }
                default:break;
            }
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