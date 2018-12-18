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
}
