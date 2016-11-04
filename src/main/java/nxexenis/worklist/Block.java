package nxexenis.worklist;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple tree node representing a code block
 */
public class Block
{
    private String identifier;
    private Set<Block> children;

    /**
     * Initialises a block with the given identifier and no children
     *
     * @param identifier name of the block
     */
    public Block(String identifier)
    {
        this.identifier = identifier;
        children = new HashSet<>();
    }

    /**
     * Adds a block to the child set of this block
     *
     * @param child block to be added
     * @return true if this set did not already contain the specified element
     */
    public boolean addChild(Block child)
    {
        return children.add(child);
    }

    /* Getters */

    /**
     * @return name of the block
     */
    public String getIdentifier()
    {
        return identifier;
    }

    /**
     * Returns a copy of the child set
     *
     * @return copy of the child set
     */
    public Set<Block> getChildren()
    {
        return new HashSet<>(children);
    }

    /* Setters */

    /**
     * @param identifier new name of the block
     */
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    /**
     * @return name of the block
     */
    public String toString()
    {
        return getIdentifier();
    }

    /**
     * Recursively constructs a string including the block name as well as its children.
     * Used for debugging
     *
     * @return string representation of the tree rooted at this block
     */
    public String blockTreeString()
    {
        StringBuilder sb = new StringBuilder(identifier);
        if (children.size() > 0)
        {
            String padding = "  ";
            sb.append("\n{");
            for (Block b : children)
            {
                sb.append("\n").append(padding).append(b.blockTreeString());
            }
            sb.append("\n}");
        }
        return sb.toString();
    }
}
