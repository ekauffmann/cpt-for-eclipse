package org.processmining.configurableprocesstree.parser.factories;

import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;

import java.util.ArrayList;

public interface CPTElementFactory {
    CPTNode buildNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children);
    CPTNode buildNodeForLabel();
}
