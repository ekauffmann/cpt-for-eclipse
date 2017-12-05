package org.processmining.configurableprocesstree.cptimpl.nodes;

import java.util.ArrayList;

public class Hidden extends AbstractCPTNode {
    public Hidden(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        super("TAU", "\u03C4", label, children);
    }

    public Hidden() {
        super("TAU", "\u03C4", new ArrayList<CPTNode>(), new ArrayList<CPTNode>());
    }

    @Override
    public CPTNode newCleanDuplicate() {
        return new Hidden();
    }

    @Override
    public boolean isConsistent() {
        return true;
    }

    @Override
    public CPTNode propagateBlocking() {
        return this;
    }

}
