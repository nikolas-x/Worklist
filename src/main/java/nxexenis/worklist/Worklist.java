package nxexenis.worklist;

import java.util.*;

public class Worklist
{
    private Block start;
    private Block end;
    private Set<Block> blocks;
    private boolean endReachable;
    private List<WorklistStep> steps;

    public Worklist()
    {
        start = new Block("start");
        end = new Block("end");
        blocks = new HashSet<>();
        endReachable = false;
        steps = null;
    }

    public Set<Block> execute()
    {
        if (!endReachable)
        {
            throw new IllegalStateException("Must have at least one path leading to the end block.");
        }

        Set<Block> W = new HashSet<>();
        Set<Block> R = new HashSet<>();
        List<WorklistStep> steps = new ArrayList<>();
        WorklistStep currentStep;
        int stepCounter = 0;

        W.add(start);
        currentStep = new WorklistStep(stepCounter++, R, W);
        steps.add(currentStep);
        while (!W.isEmpty())
        {
            R.addAll(W);
            for (Block b : new HashSet<>(W))
            {
                blocks.add(b); // In case this block was not added beforehand
                W.addAll(b.getChildren());
                W.removeAll(R);
            }

            // Save the current step
            currentStep = new WorklistStep(stepCounter++, R, W);
            steps.add(currentStep);
        }

        this.steps = steps;

        Set<Block> unreachable = new HashSet<>(blocks);
        unreachable.removeAll(R);
        return unreachable;
    }

    public boolean addToStart(Block... blocks)
    {
        this.blocks.addAll(Arrays.asList(blocks));
        return start.addChildren(blocks);
    }

    public boolean add(Block... blocks)
    {
        return this.blocks.addAll(Arrays.asList(blocks));
    }

    public boolean addToEnd(Block... blocks)
    {
        this.blocks.addAll(Arrays.asList(blocks));
        endReachable = true;
        return end.addChildren(blocks);
    }

    public List<WorklistStep> getSteps()
    {
        if (steps == null)
        {
            throw new IllegalStateException("Cannot retreive steps before execution.");
        }
        return new ArrayList<>(steps);
    }

    public String toString()
    {
        return blocks.toString();
    }

    /**
     * Internal class for holding data at each step
     */
    public class WorklistStep
    {
        private int stepNumber;
        private Set<Block> R;
        private Set<Block> W;

        WorklistStep(int stepNumber, Set<Block> R, Set<Block> W)
        {
            this.stepNumber = stepNumber;
            this.R = new HashSet<>(R);
            this.W = new HashSet<>(W);
        }

        public int getNumber()
        {
            return stepNumber;
        }

        public Set<Block> getR()
        {
            return new HashSet<>(R);
        }

        public Set<Block> getW()
        {
            return new HashSet<>(W);
        }

        public String toString()
        {
            return new StringBuilder("step: ")
                    .append(stepNumber)
                    .append(" | ")
                    .append("R: ")
                    .append(R)
                    .append(" | ")
                    .append("W: ")
                    .append(W)
                    .toString();
        }
    }
}
