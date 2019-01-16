package solver;

public class Main {

    private String fileNameSource;
    private String fileNameAnswer;
    private Matrix matrix;
    private Solver solver;
    private Answer answer;

    public static void main(String[] args) {
        Main m = new Main();
        m.fileNameSource = args[0];
        m.fileNameAnswer = args[1];
        m.matrix = new Matrix(m.fileNameSource);
        m.solver = new Solver(m.matrix);
        m.answer = new Answer(m.fileNameAnswer);

        double[][] gaussJordanMatrix = m.solver.calcGaussJordan(m.matrix.getMatrix());

        if(m.solver.noSolutions){
            m.answer.writeAnswer("No Solution");
        }else if(m.solver.infinitySolutions){
            m.answer.writeAnswer("Infinity Solutions");
        }else {
            m.answer.writeAnswer(gaussJordanMatrix);
        }

        //============================TESTS======================//

    }
}