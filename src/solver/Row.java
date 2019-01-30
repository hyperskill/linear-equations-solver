package solver;

import java.util.Arrays;

public class Row {
    private double[] row;

    Row(double[] row) {
        setRow(row);
    }

    Row(int m) {
        row = new double[m];
    }

    Row normalizeRow(int index) {
        return divide(row[index]);
    }

    Row divide(double v) {
        for(int i = 0; i < row.length; i++) {
            row[i] = row[i] / v;
        }
        return this;
    }

    Row subtract(Row row) {
        for(int i = 0; i < this.row.length; i++) {
            this.row[i] = this.row[i] - row.get(i);
        }
        return this;
    }

    public double[] getRow() {
        return Arrays.copyOf(row, row.length);
    }

    public void setRow(double[] row) {
        this.row = Arrays.copyOf(row, row.length);
    }

    public void set(int j, double value) {
        this.row[j] = value;
    }

    public double get(int j) {
        return this.row[j];
    }

    public int size() {
        return this.row.length;
    }
}
