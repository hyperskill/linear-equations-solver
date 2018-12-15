package GaussianElimintation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс строки матрицы или же представление линейного уравнения через вектор коэффициентов
 */

public class Row {
    private List<Double> doubleList;

    public Row(double... array) {
        doubleList = new ArrayList<Double>();
        for (double one : array) {
            doubleList.add(one);
        }
    }

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
        Double[] to = new Double[doubleList.size()],
                what = new Double[row.doubleList.size()];
        to = doubleList.toArray(to);
        what = row.doubleList.toArray(what);
        for (int counter = skipFirst; counter < doubleList.size(); counter++) {
            to[counter] += what[counter] * coefficient;
        }
        doubleList = new ArrayList<>(Arrays.asList(to));
    }

    public Double getDouble(int index) {
        return doubleList.get(index);
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
