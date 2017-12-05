package org.processmining.configurableprocesstree.parser.factories;

import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;
import org.processmining.configurableprocesstree.cptimpl.nodes.Seq;

import java.util.ArrayList;

public class SeqFactory implements CPTElementFactory {
    @Override
    public CPTNode buildNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        return new Seq(name, label, children);
    }

    @Override
    public CPTNode buildNodeForLabel() {
        return new Seq();
    }
}
