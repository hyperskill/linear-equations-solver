import org.junit.Assert;
import org.junit.Test;
import solver.NumberSolutions;
import solver.Solver;

import java.util.Scanner;

public class SolverTest {
    @Test
    public void constructor() {
        final Scanner sc = new Scanner("1 1\n1 2");
        final Solver p = new Solver(sc);
        Assert.assertEquals(1, p.getSize());
    }

    @Test
    public void solve1() {
        final Scanner sc = new Scanner("1 1\n2 4");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{2.0}, p.getSolution(), 0.0000001);
    }

    @Test
    public void solve2() {
        final Scanner sc = new Scanner("2 2\n1 2 3\n4 5 6");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{-1.0, 2.0}, p.getSolution(), 0.0000001);
    }

    @Test
    public void solve3() {
        final Scanner sc = new Scanner("2 2\n4 5 7\n3 9 9");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{0.85714, 0.71429}, p.getSolution(), 0.00001);
    }

    @Test
    public void solve4() {
        final Scanner sc = new Scanner("3 3\n1 1 2 9\n2 4 -3 1\n3 6 -5 0");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{1.0, 2.0, 3.0}, p.getSolution(), 0.0000001);
    }

    @Test
    public void solve5() {
        final Scanner sc = new Scanner("2 2\n0 1 1\n1 0 1");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{1.0, 1.0}, p.getSolution(), 0.0000001);
    }

    @Test
    public void solve6() {
        final Scanner sc = new Scanner("2 2\n0 1 1\n0 2 2");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{0.0, 1.0}, p.getSolution(), 0.0000001);
    }

    @Test
    public void solve7() {
        final Scanner sc = new Scanner("2 2\n0 1 1\n0 2 3");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.NONE, p.getNumberSolutions());
        Assert.assertArrayEquals(null, p.getSolution(), 0.0000001);
    }

    @Test
    public void solve8() {
        final Scanner sc = new Scanner("3 4\n0 1 2 9\n0 1 3 1\n1 0 6 0\n2 0 2 0");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.NONE, p.getNumberSolutions());
        Assert.assertArrayEquals(null, p.getSolution(), 0.0000001);
    }

    @Test
    public void solve9() {
        final Scanner sc = new Scanner("3 1\n1 1 2 9");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{9.0, 0.0, 0.0}, p.getSolution(), 0.0000001);
    }
}
