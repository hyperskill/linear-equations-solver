// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
import org.junit.Assert;
import org.junit.Test;
import solver.Complex;

public class ComplexTest {
    @Test
    public void equals1() {
        final Complex a = new Complex(1.0, 2.0);
        final Complex b = new Complex(1.0, 2.0);
        Assert.assertEquals(a, b);
    }

    @Test
    public void equals2() {
        final Complex a = new Complex(1.0, 2.0);
        Assert.assertNotEquals("Hello", a);
    }

    @Test
    public void equals3() {
        final Complex a = new Complex(1.0, 2.0);
        final Complex b = new Complex(1.1, 2.0);
        Assert.assertNotEquals(b, a);
    }

    @Test
    public void equals4() {
        final Complex a = new Complex(1.0, 2.1);
        final Complex b = new Complex(1.0, 2.0);
        Assert.assertNotEquals(b, a);
    }

    @Test
    public void toString1() {
        final Complex a = new Complex(1.0, 0.0);
        Assert.assertEquals("1", a.toString());
    }

    @Test
    public void toString2() {
        final Complex a = new Complex(0.0, 1.0);
        Assert.assertEquals("1i", a.toString());
    }

    @Test
    public void toString3() {
        final Complex a = new Complex(-2.4, 1.5);
        Assert.assertEquals("-2.4+1.5i", a.toString());
    }

    @Test
    public void toString4() {
        final Complex a = new Complex(2.4, -1.5);
        Assert.assertEquals("2.4-1.5i", a.toString());
    }

    @Test
    public void toString5() {
        final Complex a = new Complex(0, 0);
        Assert.assertEquals("0", a.toString());
    }

    @Test
    public void defaultConstructor() {
        final Complex c = new Complex();
        Assert.assertEquals(0.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(0.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor1() {
        final Complex c = new Complex(1.0, 0.0);
        Assert.assertEquals(1.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(0.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor2() {
        boolean ok = false;
        try {
            @SuppressWarnings("unused") final Complex c = new Complex("gbc");
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void constructor3() {
        final String s = "-1.3";
        final Complex c = new Complex(s);
        Assert.assertEquals(-1.3, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(0.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor4() {
        final String s = "2.5i";
        final Complex c = new Complex(s);
        Assert.assertEquals(0.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(2.5, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor5() {
        final String s = "1.3-2.5i";
        final Complex c = new Complex(s);
        Assert.assertEquals(1.3, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(-2.5, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor6() {
        final String s = "1";
        final Complex c = new Complex(s);
        Assert.assertEquals(1.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(0.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor7() {
        boolean ok = false;
        try {
            @SuppressWarnings("unused") final Complex c = new Complex("1.3-2.5");
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void constructor8() {
        boolean ok = false;
        try {
            @SuppressWarnings("unused") final Complex c = new Complex("1.3i-2.5");
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void constructor9() {
        boolean ok = false;
        try {
            @SuppressWarnings("unused") final Complex c = new Complex("1.3ij");
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void constructor10() {
        boolean ok = false;
        try {
            @SuppressWarnings("unused") final Complex c = new Complex("1.3+3.5546ij");
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void constructor11() {
        boolean ok = false;
        try {
            @SuppressWarnings("unused") final Complex c = new Complex("1.3ji");
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void constructor12() {
        boolean ok = false;
        try {
            @SuppressWarnings("unused") final Complex c = new Complex("1.3+3.5546ji");
        } catch (Exception e) {
            ok = true;
        }
        Assert.assertTrue(ok);
    }

    @Test
    public void constructor13() {
        final String s = "i";
        final Complex c = new Complex(s);
        Assert.assertEquals(0.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(1.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor14() {
        final String s = "-i";
        final Complex c = new Complex(s);
        Assert.assertEquals(0.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(-1.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor15() {
        final String s = "0.5+i";
        final Complex c = new Complex(s);
        Assert.assertEquals(0.5, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(1.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor16() {
        final String s = "0.5-i";
        final Complex c = new Complex(s);
        Assert.assertEquals(0.5, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(-1.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void constructor17() {
        final String s = "+i";
        final Complex c = new Complex(s);
        Assert.assertEquals(0.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(1.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void add() {
        final Complex a = new Complex(3.0, -5.0);
        final Complex b = new Complex(4.0, 2.0);
        final Complex c = Complex.add(a, b);
        Assert.assertEquals(7.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(-3.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void multiply() {
        final Complex a = new Complex(3.0, 2.0);
        final Complex b = new Complex(1.0, 7.0);
        final Complex c = Complex.multiply(a, b);
        Assert.assertEquals(-11.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(23.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void conjugate() {
        final Complex a = new Complex(3.0, 2.0);
        final Complex c = a.conjugate();
        Assert.assertEquals(3.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(-2.0, c.getImag(), Complex.EPSILON);
    }

    @Test
    public void divide() {
        final Complex a = new Complex(2.0, 3.0);
        final Complex b = new Complex(4.0, -5.0);
        final Complex c = Complex.divide(a, b);
        Assert.assertEquals(-7.0 / 41.0, c.getReal(), Complex.EPSILON);
        Assert.assertEquals(22.0 / 41.0, c.getImag(), Complex.EPSILON);
    }
}
