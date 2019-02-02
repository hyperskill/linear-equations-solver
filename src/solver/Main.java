package solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String in = args[1];
        String out = args[3];

        File file = new File(in);

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine())
                System.out.println(scanner.nextLine());

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}