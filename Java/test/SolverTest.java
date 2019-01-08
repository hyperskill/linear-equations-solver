import org.junit.Assert;
import org.junit.Test;
import solver.Complex;
import solver.NumberSolutions;
import solver.Solver;

import java.util.Scanner;

public class SolverTest {
    @Test
    public void constructor1() {
        try {
            final Scanner sc = new Scanner("1 1\n1 2");
            @SuppressWarnings("unused") final Solver p = new Solver(sc);
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void constructor2() {
        boolean ok = false;
        try {
            final Scanner sc = new Scanner("1 1\nab 2");
            @SuppressWarnings("unused") final Solver p = new Solver(sc);
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void constructor3() {
        boolean ok = false;
        try {
            final Scanner sc = new Scanner("-1 1\n2 2");
            @SuppressWarnings("unused") final Solver p = new Solver(sc);
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void constructor4() {
        boolean ok = false;
        try {
            final Scanner sc = new Scanner("1 -1\n2 2");
            @SuppressWarnings("unused") final Solver p = new Solver(sc);
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void solve0() {
        final Scanner sc = new Scanner("1   1 \n\n 2 4");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(2.0, 0)};

        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(null, p.getSolutionGeneral());
    }

    @Test
    public void solve1() {
        final Scanner sc = new Scanner("1 1\n2 4");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(2.0, 0)};

        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(null, p.getSolutionGeneral());
    }

    @Test
    public void solve2() {
        final Scanner sc = new Scanner("2 2\n1 2 3\n4 5 6");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(-1.0, 0),
                new Complex(2.0, 0)};

        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(null, p.getSolutionGeneral());
    }

    @Test
    public void solve3() {
        final Scanner sc = new Scanner("2 2\n4 5 7\n3 9 9");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(0.85714, 0),
                new Complex(0.71429, 0)};

        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(null, p.getSolutionGeneral());
    }

    @Test
    public void solve4() {
        final Scanner sc = new Scanner("3 3\n1 1 2 9\n2 4 -3 1\n3 6 -5 0");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(1.0, 0),
                new Complex(2.0, 0), new Complex(3.0, 0)};

        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(null, p.getSolutionGeneral());
    }

    @Test
    public void solve5() {
        final Scanner sc = new Scanner("2 2\n0 1 1\n1 0 1");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(1.0, 0),
                new Complex(1.0, 0)};

        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(null, p.getSolutionGeneral());
    }

    @Test
    public void solve6() {
        final Scanner sc = new Scanner("2 2\n0 1 1\n0 2 2");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(0.0, 0),
                new Complex(1.0, 0)};
        final String[] expectedGeneralSolution = new String[]{"x1", "1"};

        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(expectedGeneralSolution, p.getSolutionGeneral());
    }

    @Test
    public void solve7() {
        final Scanner sc = new Scanner("2 2\n0 1 1\n0 2 3");
        final Solver p = new Solver(sc);
        p.solve();

        Assert.assertEquals(NumberSolutions.NONE, p.getNumberSolutions());
        Assert.assertArrayEquals(null, p.getSolutionPartial());
        Assert.assertArrayEquals(null, p.getSolutionGeneral());
    }

    @Test
    public void solve8() {
        final Scanner sc = new Scanner("3 4\n0 1 2 9\n0 1 3 1\n1 0 6 0\n2 0 2 0");
        final Solver p = new Solver(sc);
        p.solve();

        Assert.assertEquals(NumberSolutions.NONE, p.getNumberSolutions());
        Assert.assertArrayEquals(null, p.getSolutionPartial());
        Assert.assertArrayEquals(null, p.getSolutionGeneral());
    }

    @Test
    public void solve9() {
        final Scanner sc = new Scanner("3 1\n1 1 2 9");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(9.0, 0),
                new Complex(0, 0), new Complex(0, 0)};
        final String[] expectedGeneralSolution = new String[]{"9 - x2 - x3 * (2)", "x2", "x3"};

        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(expectedGeneralSolution, p.getSolutionGeneral());
    }

    @Test
    public void solve10() {
        final Scanner sc = new Scanner("4 4\n1 0 0 5 0\n0 0 0 0 0\n0 0 1 4 6\n0 0 5 5 5");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(-8.3333333, 0),
                new Complex(0, 0), new Complex(-0.6666667, 0), new Complex(1.6666667, 0)};
        final String[] expectedGeneralSolution = new String[]{"-8.3333", "x2", "-0.6667", "1.6667"};

        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(expectedGeneralSolution, p.getSolutionGeneral());
    }

    @Test
    public void solve11() {
        final Scanner sc = new Scanner("4 4\n2 3 -1 1 1\n8 12 -9 8 3\n4 6 3 -2 3\n2 3 9 -7 3");
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(0.6, 0),
                new Complex(0, 0), new Complex(0.2, 0), new Complex(0, 0)};
        final String[] expectedGeneralSolution = new String[]{"0.6 - x2 * (1.5) - x4 * (0.1)", "x2",
                "0.2 - x4 * (-0.8)", "x4"};

        Assert.assertEquals(NumberSolutions.MANY, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(expectedGeneralSolution, p.getSolutionGeneral());
    }

    @Test
    public void solve12() {
        final String source = "3 3\n1+2i -1.5-1.1i 2.12 91+5i\n-1+3i 1.2+3.5i -3.3 1+15i\n12.31 1.3-5i 12.3i -78.3i";
        final Scanner sc = new Scanner(source);
        final Solver p = new Solver(sc);
        p.solve();

        final Complex[] expectedPartialSolution = new Complex[]{new Complex(6.73335286, -22.99754223),
                new Complex(-1.7976071, 2.08404919), new Complex(15.69938581, 7.3960106)};

        Assert.assertEquals(NumberSolutions.ONE, p.getNumberSolutions());
        Assert.assertArrayEquals(expectedPartialSolution, p.getSolutionPartial());
        Assert.assertArrayEquals(null, p.getSolutionGeneral());
    }
}
