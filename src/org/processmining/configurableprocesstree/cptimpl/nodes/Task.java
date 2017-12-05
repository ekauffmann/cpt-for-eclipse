package org.processmining.configurableprocesstree.cptimpl.nodes;

import java.util.ArrayList;

public class Task extends AbstractCPTNode {
    public Task(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        super(name, label);
    }

    public Task() {
        super("Task", new ArrayList<CPTNode>());
    }

    @Override
    public boolean isConsistent() {
        return this.children.isEmpty();
    }

    @Override
    public String structureTextRepresentation() {
        return "LEAF:" + this.name;
    }

    @Override
    public CPTNode newCleanDuplicate() {
        return new Task(this.name, new ArrayList<CPTNode>(), new ArrayList<CPTNode>());
    }

    @Override
    public CPTNode propagateBlocking() {
        return this;
    }
}
