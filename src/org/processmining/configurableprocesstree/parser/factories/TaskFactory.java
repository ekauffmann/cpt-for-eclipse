package org.processmining.configurableprocesstree.parser.factories;

import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;
import org.processmining.configurableprocesstree.cptimpl.nodes.Task;

import java.util.ArrayList;

public class TaskFactory implements CPTElementFactory {
    @Override
    public CPTNode buildNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        return new Task(name, label, children);
    }

    @Override
    public CPTNode buildNodeForLabel() {
        return new Task();
    }


}
