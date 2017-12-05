package org.processmining.configurableprocesstree.parser.factories;

import org.processmining.configurableprocesstree.cptimpl.nodes.Blocked;
import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;

import java.util.ArrayList;

public class BlockedFactory implements CPTElementFactory {
    @Override
    public CPTNode buildNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        return new Blocked(name, label, children);
    }

    @Override
    public CPTNode buildNodeForLabel() {
        return new Blocked();
    }


}
