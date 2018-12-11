package GaussianElimintation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinearEquation {
    protected List<Double> coefficients = new ArrayList<Double>();
    protected Double rightSide;

    public LinearEquation(Double rightSide, Double... coefficients) {
        this.rightSide = rightSide;
        this.coefficients.addAll(Arrays.asList(coefficients));
    }

    public LinearEquation(Double[] array) {
        this(array[array.length - 1], Arrays.copyOf(array, array.length - 1));
    }

    public Row toRow() {
        List<Double> buffer = new ArrayList<Double>(coefficients);
        buffer.add(rightSide);
        return new Row((Double[]) buffer.toArray());
    }

    public List<Double> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Double> coefficients) {
        this.coefficients = coefficients;
    }
}
