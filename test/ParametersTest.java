import org.junit.Assert;
import org.junit.Test;
import solver.Parameters;

public class ParametersTest {
    @Test
    public void defaultParameters() {
        Parameters p = new Parameters(new String[]{});
        Assert.assertEquals("input.txt", p.in);
        Assert.assertEquals("output.txt", p.out);
    }

    @Test
    public void in() {
        Parameters p = new Parameters(new String[]{"-in", "in.txt"});
        Assert.assertEquals("in.txt", p.in);
    }

    @Test
    public void out() {
        Parameters p = new Parameters(new String[]{"-out", "out2.txt"});
        Assert.assertEquals("out2.txt", p.out);
    }
}
