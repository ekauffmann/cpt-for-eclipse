package org.processmining.configurableprocesstree.cptimpl;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;

import java.util.ArrayList;
import java.util.Map;


public class ConfigurableProcessTree {
    private CPTNode root;
    private String name;
    private ArrayList<String> configurations;

    public ConfigurableProcessTree(CPTNode root, String name, ArrayList<String> configurations) {
        this.root = root;
        this.root.setIsRoot(true);
        this.name = name.replace(".cpt", "");
        this.configurations = configurations;
    }

    public ConfigurableProcessTree(CPTNode root, String name) {
        // build default empty configuration
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < root.numberOfNodes() - 1; i++) {
            stringBuilder.append("-,");
        }
        stringBuilder.append("-");

        ArrayList<String> configs = new ArrayList<>();
        configs.add(stringBuilder.toString());

        this.root = root;
        this.name = name.replace(".cpt", "");
        this.configurations = configs;
    }

    public CPTNode getRoot() {
        return root;
    }

    public String getName() {
        return this.name;
    }

    public mxGraph buildMxGraph() {
        mxGraph graph = new mxGraph();

        Object parent = graph.getDefaultParent();

        Map<String, Object> edgeStyle = graph.getStylesheet().getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "black");
        edgeStyle.put(mxConstants.STYLE_FILLCOLOR, "black");

        Map<String, Object> vertexStyle = graph.getStylesheet().getDefaultVertexStyle();
        vertexStyle.put(mxConstants.STYLE_ROUNDED, "1");
        vertexStyle.put(mxConstants.STYLE_STROKECOLOR, "black");
        vertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#c4c4c4");
        vertexStyle.put(mxConstants.STYLE_FONTCOLOR, "black");
        vertexStyle.put(mxConstants.STYLE_SPACING, "5");

        graph.getModel().beginUpdate();
        try {
            this.root.addToGraphModel(graph, parent);
        } finally {
            graph.getModel().endUpdate();
        }


        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());
        layout.setDisableEdgeStyle(false);

        graph.setAutoSizeCells(true);
        straightenEdgesAndResizeCells(graph);
        graph.setCellsEditable(false);
        graph.setCellsSelectable(false);
        graph.setCellsLocked(true);

        return graph;
    }

    public boolean isConsistent() {
        return this.root.isConsistent();
    }

    public ArrayList<ConfigurableProcessTree> configure() {
        ArrayList<ConfigurableProcessTree> configs = new ArrayList<>();

        for (int i = 0; i < this.configurations.size(); i++) {
            CPTNode appliedConfig = this.root.applyConfiguration(i);
            String configName = this.name + "_" + (i+1);
            configs.add(new ConfigurableProcessTree(appliedConfig, configName));
        }

        return configs;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.root.structureTextRepresentation());
        stringBuilder.append(" ");
        stringBuilder.append("[");
        for(String config : this.configurations) {
            stringBuilder.append("[");
            stringBuilder.append(config.replace(",", ", "));
            stringBuilder.append("]");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private void straightenEdgesAndResizeCells(mxGraph graph) {
        graph.clearSelection();
        graph.selectAll();
        Object[] cells = graph.getSelectionCells();

        for (Object c : cells) {

            mxCell cell = (mxCell) c;
            if (cell.isEdge()) {
                graph.getModel().beginUpdate();
                try {
                    mxGeometry geometry = (mxGeometry) graph.getModel().getGeometry(cell).clone();
                    geometry.setPoints(null);
                    graph.getModel().setGeometry(cell, geometry);
                } finally {
                    graph.getModel().endUpdate();
                }
            }
            else if (cell.isVertex()) {
                graph.updateCellSize(c);
            }
        }
    }
}
