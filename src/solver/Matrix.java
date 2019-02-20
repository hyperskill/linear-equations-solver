package solver;

import java.util.Arrays;

class Pos{
    int x;
    int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Matrix {
    int N;//количество строк
    int M;//количество столбцов
    Row[] rows;
    int[] colMove;

    public Matrix(int n, int m) {
        N = n;
        M = m;
        rows = new Row[n];
        colMove = new int[m];//сюда будем записывать перемещения столбов
        for (int i = 0; i < M; i++)
            this.colMove[i] = i;
    }

    public void addRow(int index, String str) throws ParsingArrayException {
        this.rows[index] = new Row(index+1, M+1, this);
        this.rows[index].fillRow(str);
    }

    public void print(){
        for (Row r:this.rows) {
            System.out.println(r.asString());
        }
    }

    public String printSolution(){
        String sOut = "The solution is: (";
        String result="";
        double s;
        for (Row r:this.rows) {
            s =r.getCol(this.M);
            result+=s+"\n";
            sOut += s+", ";
        }
        System.out.println(sOut.replaceAll(",\\s$", ")"));
        return result;
    }

    public String swapRows(int src, int dest){
        Row r1 = this.rows[src];
        Row r2 = this.rows[dest];
        String result = r1.getName()+"<->"+r2.getName();
        Row tmp;
        tmp = this.rows[dest];
        this.rows[dest] = this.rows[src];
        this.rows[src] = tmp;
        this.rows[src].setMoved(true,src+1);
        this.rows[dest].setMoved(true,dest+1);
        return result;
    }


    public String swapColumns(int src, int dest){
        for (Row r:this.rows) {
            r.swapCells(src,dest);
        }
        int tmp =this.colMove[dest];
        this.colMove[dest] = this.colMove[src];
        this.colMove[src] = tmp;
        System.out.println(Arrays.toString(this.colMove));
        return String.format("C%d<->C%d",src,dest);
    }

    public int findNonZeroBelow(int col, Row rowBelow){
        for (int i = rowBelow.getRowNum()/*-1+1*/; i < N; i++) {
            if (this.rows[i].getCol(col)!=0) return i;
        }
        return -1;
    }

    public int findNonZeroRighter(int colAfterIndex, Row row){
        for (int j = colAfterIndex+1; j < M; j++) {
            if (row.getCol(j)!=0) return j;
        }
        return -1;
    }

    public Pos findNonZeroEverywere(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (this.rows[i].getCol(j)!=0) return new Pos(i,j);
            }
        }
        return null;
    }
}
