package GaussianElimintation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс матрицы, поддерживающий только элементарные преобразования умножения и складывания только со строками матрицы.
 */

public class Matrix {
    private List<Row> rows;
    private int N;

    public Matrix(double[][] matrix) {
        rows = new ArrayList<Row>();
        for (double[] row : matrix) {
            rows.add(new Row(row));
        }
        N = rows.size();
    }

    public Matrix(Double[][] matrix) {
        rows = new ArrayList<Row>();
        for (Double[] row : matrix) {
            rows.add(new Row(row));
        }
        N = rows.size();
    }

    public Matrix(Row... rows) {
        this.rows = new ArrayList<Row>(Arrays.asList(rows));
        N = this.rows.size();
    }

    public Matrix(LinearEquation... linearEquations) {
        rows = new ArrayList<Row>();
        for (LinearEquation linearEquation : linearEquations) {
            rows.add(new Row(linearEquation));
        }
        N = rows.size();
    }

    public Matrix addRow(int whatIndex, int toIndex) {
        return addRow(1, whatIndex, toIndex, 0);
    }

    public Matrix addRow(int whatIndex, int toIndex, int skipFirst) {
        return addRow(1, whatIndex, toIndex, skipFirst);
    }

    public Matrix addRow(double coefficient, int whatIndex, int toIndex, int skipFirst) {
        return addRow(coefficient, rows.get(whatIndex), rows.get(toIndex), skipFirst);
    }

    public Matrix addRow(Row what, Row to, int skipFirst) {
        return addRow(1, what, to, skipFirst);
    }

    public Matrix addRow(Row what, Row to) {
        return addRow(1, what, to, 0);
    }

    public Matrix addRow(double coefficient, Row what, Row to, int skipFirst) {
        to.add(what, coefficient, skipFirst);
        return this;
    }

    public List<Row> getRows() {
        return rows;
    }

    public Double getElement(int rowIndex, int columnIndex) {
        return rows.get(rowIndex).getDouble(columnIndex);
    }

    public int getN() {
        return N;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        N = this.rows.size();
    }
}
