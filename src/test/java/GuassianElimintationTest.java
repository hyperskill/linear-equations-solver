import GaussianElimintation.GaussianElimintation;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class GuassianElimintationTest {
    @Test
    public void simpleTest() {
        double matrix[][] = {{1.0, 1.0, 2.0, 9.0}, {2.0, 4.0, -3.0, 1.0}, {3.0, 6.0, -5.0, 0.0}};
        double result[] = {1.0, 2.0, 3.0};
        GaussianElimintation gaussianElimintation = new GaussianElimintation(matrix);
        Assert.assertTrue(Arrays.equals(result, gaussianElimintation.getResult()));
    }

}
