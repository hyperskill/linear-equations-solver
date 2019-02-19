package solver;

public class Row {
    private int rowNum;
    private int colCnt;
    private double[] data;
    public Row(int rowNum, int colCnt/*double[] data*/){
        this.rowNum = rowNum;
        this.data = new double[colCnt];
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
        if (this.data[index]==1) return "";//"already transformed1";
        double koef = 1/this.data[index];
        this.mult(koef);
        this.data[index] = 1;
        return koef +" * "+this.getName()+" -> "+this.getName();
    }

    public String transform0(int index, Row r){
        if (this.data[index]==0) return "";//"already transformed0";
        double koef = -1 * this.data[index];
        this.add1(r,koef);
        this.data[index] = 0;
        return koef + " * "+ r.getName() + " + "+ this.getName()+" -> "+this.getName();
    }

    public double getCol(int index){
        return this.data[index];
    }

    public void fillRow(String s) throws ParsingArrayException {
        String[] str = s.split("\\s+");
        try {
            for (int i = 0; i < str.length; i++) {
                this.data[i] = Double.parseDouble(str[i]);
            }
        } catch(NumberFormatException e){
            throw new ParsingArrayException(
                    String.format("The string '%s' cannot be parsed as an array of numbers", s),
                    e);
        }
    }

    public String asString() {
        String s="";
        for (double d:this.data) {
            s +=d+" ";
        };
        return this.getName()+": "+ s.trim();
    }
}
