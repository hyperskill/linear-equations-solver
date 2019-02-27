package solver;

public class iMatrix extends Matrix {
    iRow[] rows;

    public iMatrix(int n, int m) {
        super(n,m);
        this.isComplex = true;
        isComplx = true;
        rows = new iRow[n];
    }

    public iRow getRows(int index) {
        return this.rows[index];
    }

    /*public void print(){
        if (!otladka) return;
        for (iRow r:this.rows) {
            System.out.println(r);
        }
    }*/

    public void addRow(int index, String str) throws ParsingArrayException {
        this.rows[index] = new iRow(index+1, M+1, this);
        this.rows[index].fillRow(str);
    }

    public int findNonZeroBelow(int col, iRow rowBelow){
        for (int i = rowBelow.getRowNum()/*-1+1*/; i < N; i++) {
            if (!ComplexNumber.compareComplexToDouble(this.rows[i].getColComplex(col),0)) return i;
        }
        return -1;
    }

    public int findNonZeroRighter(int colAfterIndex, iRow row){
        for (int j = colAfterIndex+1; j < M; j++) {
            if (!ComplexNumber.compareComplexToDouble(row.getColComplex(j),0)) return j;
        }
        return -1;
    }

    public Pos findNonZeroEverywhere(int colAfterIndex, iRow rowBelow){
        int fromRow=0;
        if (rowBelow == null) fromRow = 0;
        else fromRow = rowBelow.getRowNum();
        for (int i = fromRow; i < N; i++) {
            for (int j = colAfterIndex+1; j < M; j++) {
                if (!ComplexNumber.compareComplexToDouble(this.rows[i].getColComplex(j),0)) return new Pos(i,j);
            }
        }
        return null;
    }

    public Pos getNumberOfZeroRows(){
        //в ч запишем количество нулевых строк,
        //в y количество нулевых строк с ненулевой правой частью
        Pos result= new Pos(0,0);
        int cnt0 =0, cnt1 =0;
        for (iRow r:this.rows) {
            cnt0=0;
            for (int i = 0; i < M+1; i++) {
                if (!ComplexNumber.compareComplexToDouble(r.getColComplex(i),0)){
                    if (i==M) result.y++;
                    break;
                }
                  cnt0++;
            }
            if (cnt0==M+1) result.x++;
        }
        return result;
    }
}
