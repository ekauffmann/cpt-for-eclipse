package org.processmining.configurableprocesstree.cptimpl.nodes;

import java.util.ArrayList;

public class Loop extends AbstractCPTNode {
    public Loop(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        super("LOOP", "\u27F3", label, children);
    }

    public Loop() {
        super("LOOP", "\u27F3", new ArrayList<CPTNode>(), new ArrayList<CPTNode>());
    }
    @Override
    public boolean isConsistent() {
        return (this.children.size() == 3) && super.isConsistent();
    }

    @Override
    public CPTNode newCleanDuplicate() {
        return new Loop();
    }

    @Override
    public CPTNode propagateBlocking() {
        if (this.children.get(0).isBlocked() || this.children.get(2).isBlocked()) {
            if (this.isRoot()) {
                return new Hidden();
            } else {
                return new Blocked();
            }
        } else if (this.children.get(1).isBlocked()) {
            CPTNode newNode = new Seq();
            newNode.setLabel(this.label);
            newNode.addChild(this.children.get(0));
            newNode.addChild(this.children.get(2));
            return newNode;
        } else {
            return this;
        }
    }
}
