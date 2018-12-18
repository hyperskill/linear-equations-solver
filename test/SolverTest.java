import org.junit.Assert;
import org.junit.Test;
import solver.Solver;

import java.util.Scanner;

public class SolverTest {
    @Test
    public void constructor() {
        final Scanner sc = new Scanner("1\n1 2");
        final Solver p = new Solver(sc);
        Assert.assertEquals(1, p.getSize());
    }

    @Test
    public void solve() {
        final Scanner sc = new Scanner("1\n2 4");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertArrayEquals(new double[]{2.0}, p.getSolution(), 0.0000001);
    }
}
