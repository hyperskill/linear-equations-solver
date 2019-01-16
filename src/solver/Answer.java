package solver;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by DIMA, on 04.12.2018
 */
public class Answer {
    private String fileNameToWrite;

    private List<Double> createAnswer(double[][] gaussJordanMatrix){
        List<Double> answer = new ArrayList<>();
        for(int i = 0; i < gaussJordanMatrix.length; i++){
            double x = gaussJordanMatrix[i][gaussJordanMatrix[i].length-1];
            x = new BigDecimal(x).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            answer.add(x);
        }
        return answer;
    }

    Answer(String fileNameToWrite){
        this.fileNameToWrite = fileNameToWrite;
    }

    void writeAnswer(double[][] gaussJordanMatrix){
        List<Double> answer = createAnswer(gaussJordanMatrix);
        try (FileWriter fileWriter = new FileWriter(fileNameToWrite)){
            String s = answer.stream().map(String::valueOf).collect(Collectors.joining(" "));
            fileWriter.write(s);
            fileWriter.flush();
        }catch (IOException e){
            System.out.println("Wrong fileOut, please check");
            System.out.println(e.getMessage());
        }
    }

    void writeAnswer(String s){
       try (FileWriter fileWriter = new FileWriter(fileNameToWrite)){
            fileWriter.write(s);
            fileWriter.flush();
        }catch (IOException e){
            System.out.println("Wrong fileOut, please check");
            System.out.println(e.getMessage());
        }
    }





}
