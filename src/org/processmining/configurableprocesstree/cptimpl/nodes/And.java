package org.processmining.configurableprocesstree.cptimpl.nodes;

import java.util.ArrayList;

public class And extends AbstractCPTNode {
    public And(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        super("AND", "\u2227", label, children);
    }

    public And() {
        super("AND", "\u2227", new ArrayList<CPTNode>(), new ArrayList<CPTNode>());
    }

    @Override
    public CPTNode newCleanDuplicate() {
        return new And();
    }

    @Override
    public CPTNode propagateBlocking() {
        for (CPTNode child : this.children) {
            if (child.isBlocked()) {
                if (this.isRoot()) {
                    return new Hidden();
                } else {
                    return new Blocked();
                }
            }
        }
        return this;
    }
}
