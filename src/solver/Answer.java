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
    private List<Complex> answer;

    private List<Complex> createAnswer(Complex[][] gaussJordanMatrix){
        answer = new ArrayList<>();
        for(int i = 0; i < gaussJordanMatrix.length; i++){
            double xReal = gaussJordanMatrix[i][gaussJordanMatrix[i].length-1].getRealPart();
            double xImaginary = gaussJordanMatrix[i][gaussJordanMatrix[i].length-1].getImaginaryPart();
            xReal = new BigDecimal(xReal).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            xImaginary = new BigDecimal(xImaginary).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            answer.add(new Complex(xReal, xImaginary));
        }
        return answer;
    }

    Answer(String fileNameToWrite){
        this.fileNameToWrite = fileNameToWrite;
    }

    void writeAnswer(Complex[][] gaussJordanMatrix){
        List<Complex> answer = createAnswer(gaussJordanMatrix);
        try (FileWriter fileWriter = new FileWriter(fileNameToWrite)){
            StringBuilder sb = new StringBuilder();
            for(Complex c : answer){
                if(c.getRealPart() == 0 && c.getImaginaryPart() == 0){
                    sb.append(0).append(" ");
                    continue;
                }
                if(c.getRealPart() != 0){
                    sb.append(c.getRealPart());
                }
                if(c.getImaginaryPart() != 0){
                    if(c.getImaginaryPart() > 0){
                        sb.append("+");
                    }
                    sb.append(c.getImaginaryPart()).append("i");
                }
                sb.append(" ");
            }
            //String s = answer.stream().map(String::valueOf).collect(Collectors.joining(" "));
            fileWriter.write(sb.toString().trim());
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

    public List<Complex> getAnswer() {
        return answer;
    }
}
