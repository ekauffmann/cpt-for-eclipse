package org.processmining.configurableprocesstree.cptimpl.nodes;

import java.util.ArrayList;

public class Or extends AbstractCPTNode {
    public Or(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        super("OR", "\u2228", label, children);
    }

    public Or() {
        super("OR", "\u2228", new ArrayList<CPTNode>(), new ArrayList<CPTNode>());
    }


    @Override
    public CPTNode newCleanDuplicate() {
        return new Or();
    }

    @Override
    public CPTNode propagateBlocking() {
        ArrayList<CPTNode> newChildren = new ArrayList<>();
        for (CPTNode child : this.children) {
            if (!child.isBlocked()) {
                newChildren.add(child);
            }
        }
        if (newChildren.size() > 0) {
            this.setChildren(newChildren);
            return this;
        } else {
            if (this.isRoot()) {
                return new Hidden();
            } else {
                return new Blocked();}
        }
    }
}
