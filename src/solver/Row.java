package solver;

public class Row {
    private int rowNum;
    private int colCnt;

    private int oldRowNum;

    private double[] data;
    private boolean isMoved;
    private Matrix owner;
    public Row(int rowNum, int colCnt, Matrix owner/*double[] data*/){
        this.rowNum = rowNum;
        this.oldRowNum = rowNum;
        this.data = new double[colCnt];
        this.colCnt = colCnt;
        this.isMoved = false;
        this.owner = owner;
    }
    public int getRowNum() {
        return rowNum;
    }

    public int getOldRowNum() {
        return oldRowNum;
    }

    public String getName() {
        return "R"+rowNum;
    }
    public void mult(double koef){
        for (int i = 0; i < data.length; i++) {
            data[i]*=koef;
        }
    }

    public void add(Row r){
        for (int i = 0; i < data.length; i++) {
            data[i]+=r.data[i];
        }
    }

    public void add1(Row r, double koef){
        for (int i = 0; i < data.length; i++) {
            data[i]+=koef*r.data[i];
        }
    }
    //преобразуем строку, чтобы элемент с индексом index стал = 1
    //возвращаем строку с манипуляцией
    public String transform1(int index){
        //if (this.data[index]==1) return "";//"already transformed1";
        if (Matrix.compareDouble(this.data[index],1)) return "";//"already transformed1";
        //if (this.data[index]==0){
        if (Matrix.compareDouble(this.data[index],0)){
            int nonZeroInd = this.owner.findNonZeroBelow(index,this);
            if (nonZeroInd > -1)
                return this.owner.swapRows(this.rowNum-1,nonZeroInd);
            nonZeroInd = this.owner.findNonZeroRighter(index,this);
            if (nonZeroInd > -1)
                return this.owner.swapColumns(index,nonZeroInd);
            //поиск по всей матрице
            Pos pos = this.owner.findNonZeroEverywhere(index,this);
            if (pos == null) return "";
            return this.owner.swapColumns(index,pos.y)+";"+
                    this.owner.swapRows(this.rowNum-1, pos.x);
        }
        double koef = 1/this.data[index];
        this.mult(koef);
        this.data[index] = 1;
//        return String.format("%-8.2f %n",koef)  +" * "+this.getName()+" -> "+this.getName();
        return String.format("%d: %.2f * %s -> %s",++this.owner.iteration, koef,this.getName(),this.getName());
    }

    public String transform0(int index, Row r){
        if (Matrix.compareDouble(this.data[index],0)) return "";//"already transformed0";
        double koef = -1 * this.data[index];
        this.add1(r,koef);
        this.data[index] = 0;
        this.owner.iteration++;
//        return koef + " * "+ r.getName() + " + "+ this.getName()+" -> "+this.getName();
        return String.format("%d: %.3f * %s -> %s",this.owner.iteration, koef,r.getName(),this.getName());
    }

    public double getCol(int index){
        return this.data[index];
    }

    public void fillRow(String s) throws ParsingArrayException {
        String[] str = s.split("\\s+");
        try {
            if (str.length!=this.colCnt)
                throw new ParsingArrayException(
                    String.format("The string '%s' cannot be parsed correctly. It has to contain %d numbers", s, this.colCnt+1),
                    null);
            for (int i = 0; i < str.length; i++) {
                this.data[i] = Double.parseDouble(str[i]);
            }
        } catch(NumberFormatException e){
            throw new ParsingArrayException(
                    String.format("The string '%s' cannot be parsed as an array of numbers", s),
                    e);
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new ParsingArrayException(
                    String.format("The string '%s' cannot be parsed correctly, count of numbers is incorrect", s),
                    e);
        }
    }

    public String asString() {
        String s="";
        for (double d:this.data) {
            s +=String.format("%-10.3f ",d);
            //s +=String.format("%.3f ",d);
        };
        return this.getName()+((this.getOldRowNum()!=this.getRowNum())?String.format("(%d)",this.getOldRowNum()):"")+": "+ s.trim();
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setMoved(boolean moved, int newRow) {
        isMoved = moved;
        if (moved) {
            /*if (this.oldRowNum == this.rowNum) {
                this.oldRowNum = rowNum;
            }*/
            this.rowNum = newRow;
        }
        if (this.rowNum == this.oldRowNum) isMoved = false;
    }
    public void swapCells(int src, int dest){
        double tmp;
        tmp = this.data[dest];
        this.data[dest] = this.data[src];
        this.data[src] = tmp;
    }
}
