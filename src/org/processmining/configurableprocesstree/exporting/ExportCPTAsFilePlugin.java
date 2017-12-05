package org.processmining.configurableprocesstree.exporting;

import org.processmining.configurableprocesstree.cptimpl.ConfigurableProcessTree;
import org.processmining.contexts.uitopia.annotations.UIExportPlugin;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Plugin(
        name = "Export Configurable Process Tree as cpt file",
        parameterLabels = { "Configurable Process Tree" , "File" },
        returnLabels = {},
        returnTypes = {}, userAccessible = true
)
@UIExportPlugin(
        description = "Configurable Process Tree text representation (.cpt)",
        extension = "cpt"
)
public class ExportCPTAsFilePlugin {
    @UITopiaVariant(
            affiliation = "Universidad de Chile",
            author = "Elisa Kauffmann F.",
            email = "ekauffma@dcc.uchile.cl"
    )
    @PluginVariant(
            requiredParameterLabels = { 0, 1 }
    )
    public void export(PluginContext context, ConfigurableProcessTree cpt, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(cpt.toString());
        printWriter.close();
    }
}
