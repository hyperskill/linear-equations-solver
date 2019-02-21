package solver;

import java.util.Arrays;

//import java.util.Arrays;
class Pos{
    int x;
    int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String asString(){
        return this.x+","+this.y;
    }
}

public class Matrix {
    public static final double threshold = 0.000001;
    int N;//количество строк
    int M;//количество столбцов
    Row[] rows;
    int[] colMove;
    int iteration = 0;
    private boolean colsSwaped = false;

    public boolean otladka = false;

    public static boolean compareDouble(double d1, double d2){
        return (Math.abs(d1 - d2) < threshold);
    }

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
        if (!otladka) return;
        for (Row r:this.rows) {
            System.out.println(r.asString());
        }
    }

    public String printSolution(){
        if (colsSwaped) return printSolution1();
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

    private String printSolution1(){
        String sOut = "The solution is: (";
        double[] d= new double[colMove.length];
        String result="";
        for (int i = 0; i < colMove.length; i++) {
            d[colMove[i]]=this.rows[i].getCol(this.M);
        }
        for (int i = 0; i < colMove.length; i++) {
            result+=d[i]+"\n";
            sOut += d[i]+", ";
        }
        System.out.println(sOut.replaceAll(",\\s$", ")"));
        return result;
    }

    public String swapRows(int src, int dest){
        try {
            Row r1 = this.rows[src];
            Row r2 = this.rows[dest];
            iteration++;
            String result = String.format("%d: %s<->%s", iteration, r1.getName(), r2.getName());
            Row tmp;
            tmp = this.rows[dest];
            this.rows[dest] = this.rows[src];
            this.rows[src] = tmp;
            this.rows[src].setMoved(true, src + 1);
            this.rows[dest].setMoved(true, dest + 1);
            return result;
        }
        catch (Exception e){
            System.out.println(String.format("SwapRow(src=%d,dest=%d)",src,dest));
            throw e;
        }
    }


    public String swapColumns(int src, int dest){
        for (Row r:this.rows) {
            r.swapCells(src,dest);
        }
        int tmp =this.colMove[dest];
        this.colMove[dest] = this.colMove[src];
        this.colMove[src] = tmp;
        iteration++;
        colsSwaped = true;
        return String.format("%d; C%d<->C%d",iteration, src,dest);
    }

    public int findNonZeroBelow(int col, Row rowBelow){
        for (int i = rowBelow.getRowNum()/*-1+1*/; i < N; i++) {
            //if (this.rows[i].getCol(col)!=0) return i;
            if (!compareDouble(this.rows[i].getCol(col),0)) return i;
        }
        return -1;
    }

    public int findNonZeroRighter(int colAfterIndex, Row row){
        for (int j = colAfterIndex+1; j < M; j++) {
//            if (row.getCol(j)!=0) return j;
            if (!compareDouble(row.getCol(j),0)) return j;
        }
        return -1;
    }

    public Pos findNonZeroEverywhere(int colAfterIndex, Row rowBelow){
        int fromRow=0;
        if (rowBelow == null) fromRow = 0;
        else fromRow = rowBelow.getRowNum();
        for (int i = fromRow; i < N; i++) {
            for (int j = colAfterIndex+1; j < M; j++) {
//                if (this.rows[i].getCol(j)!=0) return new Pos(i,j);
                if (!compareDouble(this.rows[i].getCol(j),0)) return new Pos(i,j);
            }
        }
        return null;
    }

    public Pos getNumberOfZeroRows(){
        //в ч запишем количество нулевых строк,
        //в y количество нулевых строк с ненулевой правой частью
        Pos result= new Pos(0,0);
        int cnt0 =0, cnt1 =0;
        for (Row r:this.rows) {
            cnt0=0;
            for (int i = 0; i < M+1; i++) {
                //if (r.getCol(i)!=0) {
                if (!compareDouble(r.getCol(i),0)){
                    if (i==M) result.y++;
                    break;
                }
                cnt0++;
            }
            if (cnt0==M+1) result.x++;
        }
        return result;
    }

    public int checkSolution(){
        Pos zeroRowsCnt = this.getNumberOfZeroRows();
        if (otladka)
            System.out.println(zeroRowsCnt.asString());
        if (zeroRowsCnt.y>0) return -1;//no solutions
        int cntEq = this.N-zeroRowsCnt.x;//количество ненулевых уравнений
        if (cntEq==this.M) return 1;//one solution
        if (cntEq<this.M) return 0;//Infinite solutions
            return -1;
    }
}
