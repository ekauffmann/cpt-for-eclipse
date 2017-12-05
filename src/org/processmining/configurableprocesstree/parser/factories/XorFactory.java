package org.processmining.configurableprocesstree.parser.factories;

import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;
import org.processmining.configurableprocesstree.cptimpl.nodes.Xor;

import java.util.ArrayList;

public class XorFactory implements CPTElementFactory {
    @Override
    public CPTNode buildNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        return new Xor(name, label, children);
    }

    @Override
    public CPTNode buildNodeForLabel() {
        return new Xor();
    }
}
