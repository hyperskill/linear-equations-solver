import org.junit.Assert;
import org.junit.Test;
import solver.Complex;

public class ComplexTest {
    private final static double epsilon = 0.000001;

    @Test
    public void equals1() {
        final Complex a = new Complex(1.0, 2.0);
        final Complex b = new Complex(1.0, 2.0);
        Assert.assertTrue(a.equals(b));
    }

    @Test
    public void equals2() {
        final Complex a = new Complex(1.0, 2.0);
        Assert.assertFalse(a.equals("Hello"));
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
    public void constructor() {
        final Complex c = new Complex(1.0, 0.0);
        Assert.assertEquals(1.0, c.getReal(), epsilon);
        Assert.assertEquals(0.0, c.getImag(), epsilon);
    }

    @Test
    public void defaultConstructor() {
        final Complex c = new Complex();
        Assert.assertEquals(0.0, c.getReal(), epsilon);
        Assert.assertEquals(0.0, c.getImag(), epsilon);
    }

    @Test
    public void add() {
        final Complex a = new Complex(3.0, -5.0);
        final Complex b = new Complex(4.0, 2.0);
        final Complex c = Complex.add(a, b);
        Assert.assertEquals(7.0, c.getReal(), epsilon);
        Assert.assertEquals(-3.0, c.getImag(), epsilon);
    }

    @Test
    public void multiply() {
        final Complex a = new Complex(3.0, 2.0);
        final Complex b = new Complex(1.0, 7.0);
        final Complex c = Complex.multiply(a, b);
        Assert.assertEquals(-11.0, c.getReal(), epsilon);
        Assert.assertEquals(23.0, c.getImag(), epsilon);
    }

    @Test
    public void conjugate() {
        final Complex a = new Complex(3.0, 2.0);
        final Complex c = a.conjugate();
        Assert.assertEquals(3.0, c.getReal(), epsilon);
        Assert.assertEquals(-2.0, c.getImag(), epsilon);
    }

    @Test
    public void divide() {
        final Complex a = new Complex(2.0, 3.0);
        final Complex b = new Complex(4.0, -5.0);
        final Complex c = Complex.divide(a, b);
        Assert.assertEquals(-7.0 / 41.0, c.getReal(), epsilon);
        Assert.assertEquals(22.0 / 41.0, c.getImag(), epsilon);
    }

    @Test
    public void parseComplex1() {
        final String s = "-1.3";
        final Complex c = new Complex(s);
        Assert.assertEquals(-1.3, c.getReal(), epsilon);
        Assert.assertEquals(0.0, c.getImag(), epsilon);
    }

    @Test
    public void parseComplex2() {
        final String s = "2.5i";
        final Complex c = new Complex(s);
        Assert.assertEquals(0.0, c.getReal(), epsilon);
        Assert.assertEquals(2.5, c.getImag(), epsilon);
    }

    @Test
    public void parseComplex3() {
        final String s = "1.3-2.5i";
        final Complex c = new Complex(s);
        Assert.assertEquals(1.3, c.getReal(), epsilon);
        Assert.assertEquals(-2.5, c.getImag(), epsilon);
    }

    @Test
    public void parseComplex4() {
        final String s = "1";
        final Complex c = new Complex(s);
        Assert.assertEquals(1.0, c.getReal(), epsilon);
        Assert.assertEquals(0.0, c.getImag(), epsilon);
    }
}
