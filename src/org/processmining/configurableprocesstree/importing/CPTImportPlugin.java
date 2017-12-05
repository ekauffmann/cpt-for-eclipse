package org.processmining.configurableprocesstree.importing;

import org.processmining.configurableprocesstree.cptimpl.ConfigurableProcessTree;
import org.processmining.configurableprocesstree.exceptions.InconsistentCPTException;
import org.processmining.configurableprocesstree.parser.CPTParser;
import org.processmining.configurableprocesstree.parser.factories.*;
import org.processmining.configurableprocesstree.parser.predicates.*;
import org.processmining.contexts.uitopia.annotations.UIImportPlugin;
import org.processmining.framework.abstractplugins.AbstractImportPlugin;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;

import java.io.InputStream;
import java.util.HashMap;

@Plugin(
        name = "Import Configurable Process Tree",
        parameterLabels = { "Filename" },
        returnLabels = { "Configurable Process Tree" },
        returnTypes = {ConfigurableProcessTree.class}
    )
@UIImportPlugin(
        description = "Configurable Process Tree",
        extensions = { "cpt" }
    )
public class CPTImportPlugin extends AbstractImportPlugin {
    @Override
    protected ConfigurableProcessTree importFromStream(PluginContext context, InputStream inputStream, String filename, long fileSizeInBytes) throws Exception {
        context.getFutureResult(0).setLabel(filename);

        CPTParser parser = new CPTParser(buildRules());
        ConfigurableProcessTree cpt = parser.parseTreeFromFile(inputStream, filename);
        if (!cpt.isConsistent()) throw new InconsistentCPTException();
        return cpt;
    }

    private HashMap<Predicate, CPTElementFactory> buildRules() {
        HashMap<Predicate, CPTElementFactory> rules = new HashMap<>();
        rules.put(new AndPredicate(), new AndFactory());
        rules.put(new LoopPredicate(), new LoopFactory());
        rules.put(new OrPredicate(), new OrFactory());
        rules.put(new SeqPredicate(), new SeqFactory());
        rules.put(new TaskPredicate(), new TaskFactory());
        rules.put(new XorPredicate(), new XorFactory());
        rules.put(new NoConfigurationPredicate(), new NoConfigurationFactory());
        rules.put(new BlockedPredicate(), new BlockedFactory());
        rules.put(new HiddenPredicate(), new HiddenFactory());

        return rules;
    }
}
