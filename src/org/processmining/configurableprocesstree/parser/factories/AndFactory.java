package org.processmining.configurableprocesstree.parser.factories;

import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;
import org.processmining.configurableprocesstree.cptimpl.nodes.And;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AndFactory implements CPTElementFactory {
    @Override
    public CPTNode buildNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        return new And(name, label, children);
    }

    @Override
    public CPTNode buildNodeForLabel() {
        return new And();
    }


}
