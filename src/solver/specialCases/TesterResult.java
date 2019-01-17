package solver.specialCases;

import solver.Complex;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by DIMA, on 17.01.2019
 */
public class TesterResult {
    public static void testingResult(Complex[][] matrix, List<Complex> answer){
        for(int row = 0; row < matrix.length; row++){
            Complex resultRow = matrix[row][0].multiply(answer.get(0));
            for(int column = 1; column < matrix[0].length-1; column++){
                resultRow = matrix[row][column].multiply(answer.get(column)).adding(resultRow);
            }
            double xReal = resultRow.getRealPart();
            double xImaginary = resultRow.getImaginaryPart();
            xReal = new BigDecimal(xReal).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
            xImaginary = new BigDecimal(xImaginary).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
            resultRow = new Complex(xReal, xImaginary);
            System.out.println(resultRow + " = " + matrix[row][matrix[0].length-1]);
        }
    }
}
