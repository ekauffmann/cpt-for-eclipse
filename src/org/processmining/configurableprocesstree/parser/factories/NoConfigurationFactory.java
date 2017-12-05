package org.processmining.configurableprocesstree.parser.factories;


import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;
import org.processmining.configurableprocesstree.cptimpl.nodes.NoConfigNode;

import java.util.ArrayList;

public class NoConfigurationFactory implements CPTElementFactory {
    @Override
    public CPTNode buildNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        return new NoConfigNode(name, label, children);
    }

    @Override
    public CPTNode buildNodeForLabel() {
        return new NoConfigNode();
    }


}
