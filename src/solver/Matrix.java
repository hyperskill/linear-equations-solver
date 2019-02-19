package solver;

public class Matrix {
    int N;//количество строк и столбцов
    Row[] rows;

    public Matrix(int n) {
        N = n;
        rows = new Row[n];
    }

    public void addRow(int index, String str) throws ParsingArrayException {
        this.rows[index] = new Row(index+1, N+1);
        this.rows[index].fillRow(str);
    }

    public void print(){
        for (Row r:this.rows) {
            System.out.println(r.asString());
        }
    }

    /*public void printSolution(){
        for (Row r:this.rows) {
            System.out.println(r.asString());
        }
    }*/
}
