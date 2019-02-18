package solver;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //System.out.print("Hello world!");
        //get_in_file

        List<String> lines = Files.readAllLines( Paths.get(FILE_NAME));
        for(String line: lines){
            System.out.println(line);
        }
    }
}