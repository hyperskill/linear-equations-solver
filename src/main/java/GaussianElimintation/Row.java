package GaussianElimintation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {
    protected List<Double> doubleList;

    public Row(Double... array) {
        doubleList = new ArrayList<>(Arrays.asList(array));
    }

    public Row(LinearEquation linearEquation) {
        doubleList = new ArrayList<>(linearEquation.coefficients);
        doubleList.add(linearEquation.rightSide);
    }

    public void add(Row row) {
        add(row, 1, 0);
    }

    public void add(Row row, double coefficient) {
        add(row, coefficient, 0);
    }

    public void add(Row row, double coefficient, int skipFirst) {
        Double[] to = (Double[]) doubleList.toArray(),
                what = (Double[]) row.doubleList.toArray();
        for (int counter = skipFirst; counter < doubleList.size(); counter++) {
            to[counter] += what[counter] * coefficient;
        }
        doubleList = new ArrayList<>(Arrays.asList(to));
    }

    public LinearEquation toLinearEquation() {
        return new LinearEquation((Double[]) doubleList.toArray());
    }

    public List<Double> getDoubleList() {
        return doubleList;
    }

    public void setDoubleList(List<Double> doubleList) {
        this.doubleList = doubleList;
    }
}
