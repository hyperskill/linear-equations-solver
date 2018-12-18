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

    @Test
    public void solve2() {
        final Scanner sc = new Scanner("2\n1 2 3\n4 5 6");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertArrayEquals(new double[]{-1.0, 2.0}, p.getSolution(), 0.0000001);
    }

    @Test
    public void solve3() {
        final Scanner sc = new Scanner("2\n4 5 7\n3 9 9");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertArrayEquals(new double[]{0.85714, 0.71429}, p.getSolution(), 0.00001);
    }

    @Test
    public void solve4() {
        final Scanner sc = new Scanner("3\n1 1 2 9\n2 4 -3 1\n3 6 -5 0");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertArrayEquals(new double[]{1.0, 2.0, 3.0}, p.getSolution(), 0.0000001);
    }
}
