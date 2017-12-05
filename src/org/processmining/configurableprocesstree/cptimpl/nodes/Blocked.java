package org.processmining.configurableprocesstree.cptimpl.nodes;

import java.util.ArrayList;

import com.mxgraph.view.mxGraph;

public class Blocked extends AbstractCPTNode {
    public Blocked(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        super("BLOCKED", "B", label, children);
    }

    public Blocked() {
        super("BLOCKED", "B", new ArrayList<CPTNode>(), new ArrayList<CPTNode>());
    }
    @Override
    public Object addToGraphModel(mxGraph graph, Object parent) {
        Object currentNode = graph.insertVertex(parent, null, this.toString(), 0, 0, 50, 50, "fillCollor=black");
        for(CPTNode child : children) {
            Object childNode = child.addToGraphModel(graph, parent);
            graph.insertEdge(parent, null, "", currentNode, childNode);
        }
        return currentNode;
    }

    @Override
    public CPTNode newCleanDuplicate() {
        return new Blocked();
    }

    @Override
    public boolean isBlocked() {
        return true;
    }

    @Override
    public CPTNode propagateBlocking() {
        return new Blocked();
    }
}