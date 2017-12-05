package org.processmining.configurableprocesstree.plugins;

import org.processmining.configurableprocesstree.cptimpl.ConfigurableProcessTree;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.plugins.utils.ProvidedObjectHelper;

import java.util.ArrayList;


public class ApplyConfigurationsPlugin {
    @Plugin(
            name = "Apply Configurations of a Configurable Process Tree",
            parameterLabels = { "Configurable Process Tree" },
            returnLabels = {},
            returnTypes = {},
            help = "Produces and saves to workspace all configurations of a configurable process tree"
    )
    @UITopiaVariant(
            affiliation = "DCC, Universidad de Chile",
            author = "Elisa Kauffmann F.",
            email = "ekauffma@dcc.uchile.cl"
    )
    public void applyConfigurations(PluginContext context, ConfigurableProcessTree cpt){
        ArrayList<ConfigurableProcessTree> configurations = cpt.configure();
        context.getProgress().setMinimum(1);
        context.getProgress().setMaximum(configurations.size());
        int counter = 1;
        for(ConfigurableProcessTree config : configurations) {
            context.getProvidedObjectManager().createProvidedObject(config.getName(), config, ConfigurableProcessTree.class, context);
            context.getProgress().setValue(counter++);
            ProvidedObjectHelper.setFavorite(context, config);
        }

    }

}
