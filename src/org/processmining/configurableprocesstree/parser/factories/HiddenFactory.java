package org.processmining.configurableprocesstree.parser.factories;

import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;
import org.processmining.configurableprocesstree.cptimpl.nodes.Hidden;

import java.util.ArrayList;

public class HiddenFactory implements CPTElementFactory {
    @Override
    public CPTNode buildNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        return new Hidden(name, label, children);
    }

    @Override
    public CPTNode buildNodeForLabel() {
        return new Hidden();
    }

}
