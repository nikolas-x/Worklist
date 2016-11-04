import nxexenis.worklist.Block;
import nxexenis.worklist.Worklist;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorklistTest
{
    @Test
    public void testCorrectUnreachables()
    {
        // Define block graph
        Block b1 = new Block("B1");
        Block b2 = new Block("B2");
        Block b3 = new Block("B3");
        Block b4 = new Block("B4");
        Block b5 = new Block("B5");
        Block b6 = new Block("B6");
        Block b7 = new Block("B7");
        Block b8 = new Block("B8");

        b1.addChildren(b2, b8);
        b2.addChildren(b3);
        b3.addChildren(b4, b8);
        b6.addChildren(b5, b7);
        b7.addChildren(b3);

        Worklist worklist = new Worklist();
        worklist.addToStart(b1);
        worklist.addToEnd(b4, b8);
        worklist.add(b1, b2, b3, b4, b5, b6, b7, b8);

        Set<Block> expected = new HashSet<>(Arrays.asList(b5, b6, b7));
        Set<Block> actual = worklist.execute();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCorrectSteps()
    {
        // Define block graph
        Block b1 = new Block("B1");
        Block b2 = new Block("B2");
        Block b3 = new Block("B3");
        Block b4 = new Block("B4");
        Block b5 = new Block("B5");
        Block b6 = new Block("B6");
        Block b7 = new Block("B7");
        Block b8 = new Block("B8");

        b1.addChildren(b2, b8);
        b2.addChildren(b3);
        b3.addChildren(b4, b8);
        b6.addChildren(b5, b7);
        b7.addChildren(b3);

        Worklist worklist = new Worklist();
        worklist.addToStart(b1);
        worklist.addToEnd(b4, b8);
        worklist.add(b1, b2, b3, b4, b5, b6, b7, b8);
        worklist.execute();

        List<Worklist.WorklistStep> steps = worklist.getSteps();
        steps.forEach(System.err::println);
        Assert.assertEquals(6, steps.size());
    }
}
