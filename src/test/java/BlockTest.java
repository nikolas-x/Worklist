import nxexenis.worklist.Block;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for the Block class
 */
public class BlockTest
{

    /**
     * Ensure the child set returned is a copy of the original (i.e. not a reference)
     */
    @Test
    public void testGetChildrenCopy()
    {
        // Initialise blocks
        Block b1 = new Block("B1");
        Block b2 = new Block("B2");
        Block b3 = new Block("B3");
        Block b4 = new Block("B4");
        Block b5 = new Block("B5");

        // Add children to B1
        b1.addChild(b2);
        b1.addChild(b3);
        b1.addChild(b4);
        b1.addChild(b5);

        // Check that the returned set is a copy
        Set<Block> original = b1.getChildren();
        Set<Block> changed = b1.getChildren();
        changed.clear();
        Set<Block> current = b1.getChildren();

        // The child set within B1 should not have changed
        Assert.assertEquals(original, current);
        Assert.assertNotEquals(current, changed);
        Assert.assertNotEquals(original, changed);
    }

    /**
     * Ensure that the block tree printing works as expected
     * - Spacing is correct
     * - Correct children are printed
     * - Appropriately stop recursion when a cycle is encountered
     */
    @Test
    public void testBlockTreeString()
    {
        // Initialise blocks
        Block b1 = new Block("B1");
        Block b2 = new Block("B2");
        Block b3 = new Block("B3");
        Block b4 = new Block("B4");
        Block b5 = new Block("B5");

        // Define a block graph
        b1.addChild(b2);
        b1.addChild(b5);
        b2.addChild(b3);
        b2.addChild(b4);
        b2.addChild(b5);
        b3.addChild(b5);
        b4.addChild(b5);
        b4.addChild(b2);

        // Check the expected output vs. actual output
        String expected =
                "B1\n" +
                        "{\n" +
                        "    B5\n" +
                        "    B2\n" +
                        "    {\n" +
                        "        B5\n" +
                        "        B3\n" +
                        "        {\n" +
                        "            B5\n" +
                        "        }\n" +
                        "        B4\n" +
                        "        {\n" +
                        "            B5\n" +
                        "            B2 (visited)\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";

        String actual = b1.blockTreeString();
        Assert.assertEquals(expected, actual);
    }
}
