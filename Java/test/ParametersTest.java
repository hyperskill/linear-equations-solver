// This is an independent project of an individual developer. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
import org.junit.Assert;
import org.junit.Test;
import solver.Parameters;

public class ParametersTest {
    @Test
    public void defaultParameters() {
        final Parameters p = new Parameters(new String[]{});
        Assert.assertEquals("input.txt", p.in);
        Assert.assertEquals("output.txt", p.out);
        Assert.assertFalse(p.verbose);
    }

    @Test
    public void in() {
        final Parameters p = new Parameters(new String[]{"-in", "in.txt"});
        Assert.assertEquals("in.txt", p.in);
    }

    @Test
    public void out() {
        final Parameters p = new Parameters(new String[]{"-out", "out2.txt"});
        Assert.assertEquals("out2.txt", p.out);
    }

    @Test
    public void verbose() {
        final Parameters p = new Parameters(new String[]{"-verbose"});
        Assert.assertTrue(p.verbose);
    }
}
