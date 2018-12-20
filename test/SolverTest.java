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
        Assert.assertArrayEquals(new double[]{2.0}, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(null, p.getGeneralSolution());
    }

    @Test
    public void solve2() {
        final Scanner sc = new Scanner("2 2\n1 2 3\n4 5 6");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{-1.0, 2.0}, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(null, p.getGeneralSolution());
    }

    @Test
    public void solve3() {
        final Scanner sc = new Scanner("2 2\n4 5 7\n3 9 9");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{0.85714, 0.71429}, p.getParticularSolution(), 0.00001);
        Assert.assertArrayEquals(null, p.getGeneralSolution());
    }

    @Test
    public void solve4() {
        final Scanner sc = new Scanner("3 3\n1 1 2 9\n2 4 -3 1\n3 6 -5 0");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{1.0, 2.0, 3.0}, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(null, p.getGeneralSolution());
    }

    @Test
    public void solve5() {
        final Scanner sc = new Scanner("2 2\n0 1 1\n1 0 1");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{1.0, 1.0}, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(null, p.getGeneralSolution());
    }

    @Test
    public void solve6() {
        final Scanner sc = new Scanner("2 2\n0 1 1\n0 2 2");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{0.0, 1.0}, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(new String[]{"x1", "1,0000"}, p.getGeneralSolution());
    }

    @Test
    public void solve7() {
        final Scanner sc = new Scanner("2 2\n0 1 1\n0 2 3");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.NONE, p.getNumberSolutions());
        Assert.assertArrayEquals(null, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(null, p.getGeneralSolution());
    }

    @Test
    public void solve8() {
        final Scanner sc = new Scanner("3 4\n0 1 2 9\n0 1 3 1\n1 0 6 0\n2 0 2 0");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.NONE, p.getNumberSolutions());
        Assert.assertArrayEquals(null, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(null, p.getGeneralSolution());
    }

    @Test
    public void solve9() {
        final Scanner sc = new Scanner("3 1\n1 1 2 9");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{9.0, 0.0, 0.0}, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(new String[]{"9,0000 - x2 * (1,0000) - x3 * (2,0000)", "x2", "x3"}, p.getGeneralSolution());
    }

    @Test
    public void solve10() {
        final Scanner sc = new Scanner("4 4\n1 0 0 5 0\n0 0 0 0 0\n0 0 1 4 6\n0 0 5 5 5");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{-8.3333333, 0.0, -0.6666667, 1.6666667}, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(new String[]{"-8,3333", "x2",  "-0,6667", "1,6667"}, p.getGeneralSolution());
    }

    @Test
    public void solve11() {
        final Scanner sc = new Scanner("4 4\n2 3 -1 1 1\n8 12 -9 8 3\n4 6 3 -2 3\n2 3 9 -7 3");
        final Solver p = new Solver(sc);
        p.solve();
        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(new double[]{0.6, 0.0, 0.2, 0.0}, p.getParticularSolution(), 0.0000001);
        Assert.assertArrayEquals(new String[]{"0,6000 - x2 * (1,5000) - x4 * (0,1000)", "x2",  "0,2000 - x4 * (-0,8000)", "x4"}, p.getGeneralSolution());
    }
}
