package solver.tmpTests;

import solver.specialCases.Helper;
import solver.specialCases.SwapStore;

import java.util.Arrays;

/**
 * Created by DIMA, on 10.12.2018
 */
public class CopyArr {
    public static void main(String[] args) {
        double[][] a1 = {{1,1}, {2,2}, {0, 0}, {4,4}, {5,5}};
        double[][] a2;
        double[][] a3;

        a2 = Arrays.copyOf(a1, 2);
        Helper.print(a2);

        a3 = Arrays.copyOfRange(a1, 3, a1.length);
        Helper.print(a3);









    }
}
