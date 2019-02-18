package solver;

public class Row {
    private int rowNum;
    private double[] data;
    public Row(int rowNum, double[] data){
        this.rowNum = rowNum;
        this.data = data;
    }

    public String getName() {
        return "R"+rowNum;
    }
    public void mult(double koef){
        for (int i = 0; i < data.length; i++) {
            data[i]*=koef;
        }
    }
    /*public void divide(double koef){
        for (int i = 0; i < data.length; i++) {
            data[i]*=1/koef;
        }
    }*/
    public void add(Row r){
        for (int i = 0; i < data.length; i++) {
            data[i]+=r.data[i];
        }
    }
    //преобразуем строку, чтобы элемент с индексом index стал = 1
    //возвращаем строку с манипуляцией
    public String transform1(int index){
        double koef = 1/this.data[index];
        this.mult(koef);
        this.data[index] = 1;
        return koef +" * "+this.getName()+" -> "+this.getName();
    }

    public String transform0(int index, Row r){
        double koef = -1 * this.data[index];
        this.data[index] = 0;
        return (-1 * koef) + " * "+ r.getName() + " + "+ this.getName()+" -> "+this.getName();
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
}
