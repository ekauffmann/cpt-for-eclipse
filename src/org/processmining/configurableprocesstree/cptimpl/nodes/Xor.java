package org.processmining.configurableprocesstree.cptimpl.nodes;

import java.util.ArrayList;

public class Xor extends AbstractCPTNode {
    public Xor(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        super("XOR", "\u2715", label, children);
    }

    public Xor() {
        super("XOR", "\u2715", new ArrayList<CPTNode>(), new ArrayList<CPTNode>());
    }

    @Override
    public CPTNode newCleanDuplicate() {
        return new Xor();
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
