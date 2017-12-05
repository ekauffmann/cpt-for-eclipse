package org.processmining.configurableprocesstree.parser;

import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;

import java.util.ArrayList;

public class NodeInfo {
    private String name;
    private ArrayList<CPTNode> label;
    private int numberOfChildren;

    public NodeInfo(String name, ArrayList<CPTNode> label) {
        this.name = name;
        this.label = label;
        this.numberOfChildren = 0;
    }

    public void incrementChildren() {
        this.numberOfChildren++;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<CPTNode> getLabel() {
        return this.label;
    }
    public int getNumberOfChildren() {
        return this.numberOfChildren;
    }

}
