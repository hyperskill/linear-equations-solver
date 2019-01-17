package solver;

import solver.specialCases.Helper;
import solver.specialCases.TesterResult;

import java.util.Arrays;

public class Main {

    private String fileNameSource;
    private String fileNameAnswer;
    private Matrix matrix;
    private Solver solver;
    private Answer answer;

    public static void main(String[] args) {
        //X:\Programming\IdeaProjects\linear-equations-solver\in.txt
        Main m = new Main();
        m.fileNameSource = args[0];
        m.fileNameAnswer = args[1];
        m.matrix = new Matrix(m.fileNameSource);
        m.solver = new Solver(m.matrix);
        m.answer = new Answer(m.fileNameAnswer);
        Complex[][] origMatrix = new Complex[m.matrix.getMatrix().length][m.matrix.getMatrix()[0].length];
        for(int row = 0; row < origMatrix.length; row++){
            for(int column = 0; column < origMatrix[row].length; column++){
                origMatrix[row][column] = m.matrix.getMatrix()[row][column];
            }
        }

        Complex[][] gaussJordanMatrix = m.solver.calcGaussJordan(m.matrix.getMatrix());

        if(m.solver.noSolutions){
            m.answer.writeAnswer("No Solution");
        }else if(m.solver.infinitySolutions){
            m.answer.writeAnswer("Infinity Solutions");
        }else {
            m.answer.writeAnswer(gaussJordanMatrix);
        }



        //============================TESTS======================//
        //TesterResult.testingResult(origMatrix, m.answer.getAnswer());

    }
}