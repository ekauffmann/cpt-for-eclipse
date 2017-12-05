package org.processmining.configurableprocesstree.visualizer;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.processmining.configurableprocesstree.cptimpl.ConfigurableProcessTree;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.annotations.Plugin;

import javax.swing.*;

public class CPTVisualizer {

    @Plugin(name = "Start Configurable Process Tree Visualizer",
            returnLabels = { "Configurable Process Tree Visualizer" },
            returnTypes = { JComponent.class },
            parameterLabels = {	"Configurable Process Tree" }
            )
    @Visualizer
    public JComponent visualize(UIPluginContext context, ConfigurableProcessTree cpt) {
        JFrame frame = new JFrame();
        mxGraph graph = cpt.buildMxGraph();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setToolTips(true);
        graphComponent.setConnectable(false);

        frame.getContentPane().add(graphComponent);

        return (JComponent) frame.getContentPane();
    }
}
