package GaussianElimintation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Matrix {
    protected List<Row> rows;

    public Matrix(Row... rows) {
        this.rows = new ArrayList<Row>(Arrays.asList(rows));
    }

    public Matrix(Collection<Row> rows) {
        this.rows = new ArrayList<Row>(rows);
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
