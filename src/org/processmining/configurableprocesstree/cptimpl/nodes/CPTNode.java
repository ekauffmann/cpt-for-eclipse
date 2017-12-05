package org.processmining.configurableprocesstree.cptimpl.nodes;

import com.mxgraph.view.mxGraph;

import java.util.ArrayList;
import java.util.List;

public interface CPTNode {
    String toString();
    List<CPTNode> getChildren();
    String getSymbol();
    void setName(String name);
    void setLabel(ArrayList<CPTNode> label);
    void setIsRoot(boolean isRoot);
    void addChild(CPTNode child);
    void addChildren(List<CPTNode> children);
    void setChildren(List<CPTNode> children);
    Object addToGraphModel(mxGraph graph, Object parent);
    boolean isConsistent();
    String structureTextRepresentation();
    int numberOfNodes();
    CPTNode applyConfiguration(int index);
    CPTNode propagateBlocking();
    CPTNode reduceRedundantNodes();
    boolean doNotApplyConfig();
    boolean isBlocked();
    boolean isNoConfig();
    CPTNode newCleanDuplicate();
}
