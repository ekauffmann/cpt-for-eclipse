package org.processmining.configurableprocesstree.cptimpl.nodes;

import java.util.ArrayList;

public class NoConfigNode extends AbstractCPTNode {
    public NoConfigNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        super("-", "-", label, children);
    }

    public NoConfigNode() {
        super("-", "-", new ArrayList<CPTNode>(), new ArrayList<CPTNode>());
    }

    @Override
    public boolean doNotApplyConfig() {
        return true;
    }

    @Override
    public CPTNode newCleanDuplicate() {
        return new NoConfigNode();
    }

    @Override
    public CPTNode propagateBlocking() {
        return this;
    }

    @Override
    public boolean isNoConfig() {
        return true;
    }
}
