package org.processmining.configurableprocesstree.cptimpl.nodes;

import java.util.ArrayList;
import java.util.List;

import com.mxgraph.view.mxGraph;

public abstract class AbstractCPTNode implements CPTNode{
    String name;
    String symbol;
    ArrayList<CPTNode> label;
    List<CPTNode> children;
    boolean isRoot = false;

    AbstractCPTNode(String name, ArrayList<CPTNode> label) {
        this(name, name, label, new ArrayList<CPTNode>());
    }

    AbstractCPTNode(String name, String symbol, ArrayList<CPTNode> label, ArrayList<CPTNode> children) {
        if (label.size() == 1) { // the node is configured
            label = new ArrayList<>();
        }
        this.name = name;
        this.symbol = symbol;
        this.label = label;
        this.children = children;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(ArrayList<CPTNode> label) {
        this.label = label;
    }

    // for visualization
    @Override
    public String toString() {
        // do not display label if it is configured
        if (this.label.isEmpty()) {
            return this.getSymbol();
        } else {
            boolean isAllNoConfig = true;
            for (CPTNode config : this.label) {
                if (!config.isNoConfig()) {
                    isAllNoConfig = false;
                    break;
                }
            }

            if (isAllNoConfig) {
                return this.getSymbol();
            }
        }

        StringBuilder builder = new StringBuilder();
        for(CPTNode c : this.label) {
            builder.append(c.getSymbol());
            builder.append(", ");
        }
        builder = builder.delete(builder.length() - 2, builder.length());
        return this.symbol + "\n" + builder.toString();
    }

    @Override
    public List<CPTNode> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<CPTNode> children) {
        this.children = children;
    }

    @Override
    public void addChild(CPTNode child) {
        this.children.add(child);
    }

    @Override
    public void addChildren(List<CPTNode> children) {
        this.children.addAll(children);
    }

    @Override
    public Object addToGraphModel(mxGraph graph, Object parent) {
        Object currentNode = graph.insertVertex(parent, null, this.toString(), 0, 0, 50, 50);
        for(CPTNode child : children) {
            Object childNode = child.addToGraphModel(graph, parent);
            graph.insertEdge(parent, null, "", currentNode, childNode);
        }
        return currentNode;
    }

    @Override
    public boolean isConsistent() {
        if (this.children.isEmpty()) {
            return false;
        } else {
            for (CPTNode child : this.children) {
                if (!child.isConsistent()) return false;
            }
        }
        return true;
    }

    @Override
    public String structureTextRepresentation() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append("(");
        for(CPTNode child : this.children) {
            stringBuilder.append(child.structureTextRepresentation());
            stringBuilder.append(", ");
        }

        if (!this.children.isEmpty()) {
            // delete last ',' and ' '
            stringBuilder = stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    @Override
    public int numberOfNodes() {
        // self
        int counter = 1;
        for (CPTNode child : children) {
            counter += child.numberOfNodes();
        }

        return counter;
    }

    @Override
    public CPTNode applyConfiguration(int index) {
        // apply config to children first
        ArrayList<CPTNode> newChildren = new ArrayList<>();
        for (CPTNode child : this.children) {
            newChildren.add(child.applyConfiguration(index));
        }

        CPTNode newNode = this.label.get(index);
        if (newNode.doNotApplyConfig()) {
            newNode = this.newCleanDuplicate();
        }
        newNode.setIsRoot(this.isRoot);
        newNode.addChildren(newChildren);

        return newNode.propagateBlocking().reduceRedundantNodes();
    }

    @Override
    public CPTNode reduceRedundantNodes() {
        if (this.children.size() == 1) {
            return this.children.get(0);
        } else {
            return this;
        }
    }

    @Override
    public boolean doNotApplyConfig() {
        return false;
    }

    @Override
    public boolean isBlocked() {
        return false;
    }

    public boolean isNoConfig() {
        return false;
    }

    public boolean isRoot() {
        return this.isRoot;
    }

    public void setIsRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }
}
