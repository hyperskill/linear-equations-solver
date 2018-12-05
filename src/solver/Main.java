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
        m.solver = new Solver();
        m.answer = new Answer();

        double[][] gaussJordanMatrix = m.solver.calcGausJordan(m.matrix.getMatrix());
        m.answer.writeAnswer(m.fileNameAnswer, gaussJordanMatrix);






        //============================TESTS======================//

    }
}