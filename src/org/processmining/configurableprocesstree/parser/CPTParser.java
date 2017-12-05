package org.processmining.configurableprocesstree.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.processmining.configurableprocesstree.cptimpl.ConfigurableProcessTree;
import org.processmining.configurableprocesstree.cptimpl.nodes.CPTNode;
import org.processmining.configurableprocesstree.exceptions.EmptyFileException;
import org.processmining.configurableprocesstree.exceptions.IncorrectCPTStringFormat;
import org.processmining.configurableprocesstree.exceptions.MoreThanOneLineFileException;
import org.processmining.configurableprocesstree.exceptions.RuleNotFoundException;
import org.processmining.configurableprocesstree.parser.factories.CPTElementFactory;
import org.processmining.configurableprocesstree.parser.predicates.Predicate;

public class CPTParser {
    private HashMap<Predicate, CPTElementFactory> rules;
    private Pattern filePattern;
    private Pattern configPattern;
    private Stack<NodeInfo> nodeInfos;
    private Stack<ArrayList<CPTNode>> labelsStack;
    private Stack<CPTNode> nodesStack;
    private char childrenStart = "(".charAt(0);
    private char childrenEnd = ")".charAt(0);
    private char nodesCommaSeparator = ",".charAt(0);
    private char nodesSpaceSeparator = " ".charAt(0);
    private char leafNameStart = ":".charAt(0);

    public CPTParser(HashMap<Predicate, CPTElementFactory> rules) {
        this.rules = rules;
        this.nodeInfos = new Stack<>();
        this.labelsStack= new Stack<>();
        this.nodesStack = new Stack<>();
        this.filePattern = Pattern.compile("(.+)\\s\\[(.+)\\]");
        this.configPattern = Pattern.compile("(?>\\[([^\\[\\]]+)\\])");
    }

    public ConfigurableProcessTree parseTreeFromFile(InputStream inputStream, String filename) throws EmptyFileException, IOException, MoreThanOneLineFileException, RuleNotFoundException, IncorrectCPTStringFormat {
        String line = readFile(inputStream, filename);
        String[] tokens = parseLine(line);
        String treeStructure = tokens[0];
        ArrayList<String> configurations = getConfigurations(tokens[1]);
        CPTNode root = parseString(treeStructure, configurations);

        return new ConfigurableProcessTree(root, filename, configurations);
    }

    private String readFile(InputStream inputStream, String filename) throws IOException, EmptyFileException, MoreThanOneLineFileException {
        ArrayList<String> lines = new ArrayList<>();
        String line;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        while((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }

        if (lines.isEmpty()) {
            throw new EmptyFileException(filename);
        } else if (lines.size() > 1) {
            throw new MoreThanOneLineFileException(filename);
        }

        return lines.get(0);
    }

    private String[] parseLine(String str) throws IncorrectCPTStringFormat {
        Matcher matcher = this.filePattern.matcher(str);
        if (!matcher.matches()) {
            throw new IncorrectCPTStringFormat();
        }

        return new String[]{matcher.group(1), matcher.group(2)};
    }

    private CPTNode parseString(String treeStructure, ArrayList<String> configurations) throws IncorrectCPTStringFormat, RuleNotFoundException {

        ArrayList<ArrayList<CPTNode>> labels = this.getLabelsFromConfigurations(configurations);
        Collections.reverse(labels);
        labelsStack.addAll(labels);
        StringBuilder currentNodeName = new StringBuilder();

        // dummy initial node info to prevent stack underflow at the end
        nodeInfos.push(new NodeInfo("", new ArrayList<CPTNode>()));

        for (int index = 0; index < treeStructure.length(); index++) {
            char c = treeStructure.charAt(index);

            // start array of children. Push node name, label and new children counter to stacks
            if ( childrenStart == c) {
                try {
                    nodeInfos.push(new NodeInfo(currentNodeName.toString(), labelsStack.pop()));
                    currentNodeName = new StringBuilder();
                } catch (EmptyStackException e) {
                    throw new IncorrectCPTStringFormat();
                }
            }

            // this is a  leaf. Forget the "LEAF" part and start collecting the numbers.
            // when a "," or a ")" is reached, create new leaf, push it directly and increment counter
            else if ( leafNameStart == c ) {
                try {

                    currentNodeName = new StringBuilder();
                    char nextChar = treeStructure.charAt(index+1);
                    while (nextChar != nodesCommaSeparator && nextChar != childrenEnd) {
                        currentNodeName.append(nextChar);
                        index++;
                        nextChar = treeStructure.charAt(index+1);
                    }
                    CPTNode leaf = parseNode(currentNodeName.toString(), labelsStack.pop(), new ArrayList<CPTNode>());
                    nodesStack.push(leaf);
                    nodeInfos.peek().incrementChildren();

                    currentNodeName = new StringBuilder();
                } catch (EmptyStackException e) {
                    throw new IncorrectCPTStringFormat();
                }
            }

            // pop necessary data from stacks, build node, and push it to node stack
            else if ( childrenEnd == c ) {
                try {
                    NodeInfo nodeInfo = nodeInfos.pop();
                    ArrayList<CPTNode> children = new ArrayList<>();
                    for (int i = 0; i < nodeInfo.getNumberOfChildren(); i++) {
                        // add to first position of array. As it is popped from a stack, the order is reversed
                        children.add(0, nodesStack.pop());
                    }
                    nodesStack.push(parseNode(nodeInfo.getName(), nodeInfo.getLabel(), children));
                    nodeInfos.peek().incrementChildren();
                } catch (EmptyStackException e) {
                    throw new IncorrectCPTStringFormat();
                }
            }

            else if ( nodesCommaSeparator == c || nodesSpaceSeparator == c) {
                // this case only happens for inner nodes, so there is nothing to do. Continue checking next character.
                continue;
            }

            else {
                // add this char to the current node name
                currentNodeName.append(c);
            }

        }
        // check if parsing process went ok, i.e. stacks are empty and nodesStack contains tree root
        if (nodeInfos.size() == 1 && labelsStack.isEmpty() && nodesStack.size() == 1) {
            return nodesStack.pop();
        } else {
            throw new IncorrectCPTStringFormat();
        }
    }

    private ArrayList<String> getConfigurations(String configsStr) {
        // arraylist of all configurations
        ArrayList<String> configurations = new ArrayList<>();
        Matcher matcher = this.configPattern.matcher(configsStr);

        while (matcher.find()) {
            String aConfig = matcher.group(1).replace(" ", "");
            configurations.add(aConfig);
        }

        return configurations;
    }

    private ArrayList<ArrayList<CPTNode>> getLabelsFromConfigurations(ArrayList<String> configsArray) throws RuleNotFoundException {
        // arraylist of all configurations
        ArrayList<String[]> configs = new ArrayList<>();

        for (String config : configsArray) {
            configs.add(config.split(","));
        }

        // arraylist of labels, one for each node
        ArrayList<ArrayList<CPTNode>> labels = new ArrayList<>();
        int numberOfConfigs = configs.size();

        // if there are no configs specified in the file, return empty arraylist
        if (numberOfConfigs > 0) {
            // total number of nodes is the size of each config. Just get the size of the first one
            int numberOfNodes = configs.get(0).length;

            for (int labelIndex = 0; labelIndex < numberOfNodes; labelIndex++ ) {
                // each node label must have the same size. As many values as configs
                ArrayList<CPTNode> label = new ArrayList<>();

                // iterate through all configs, getting the element at the i-th position for the i-th node (labelIndex-th node)
                for (String[] config : configs) {
                    label.add(parseLabel(config[labelIndex]));
                }
                labels.add(label);
            }
        }
        return labels;
    }

    private CPTNode parseNode(String name, ArrayList<CPTNode> label, ArrayList<CPTNode> children) throws RuleNotFoundException {
        CPTNode node = null;

        for (Predicate predicate : rules.keySet()) {
            if (predicate.checkPredicate(name)) {
                node = rules.get(predicate).buildNode(name, label, children);
                break;
            }
        }
        if (node == null) throw new RuleNotFoundException(name);

        return node;
    }

    private CPTNode parseLabel(String name) throws RuleNotFoundException {
        CPTNode label = null;

        for (Predicate predicate : rules.keySet()) {
            if (predicate.checkPredicate(name)) {
                label = rules.get(predicate).buildNodeForLabel();
                break;
            }
        }
        if (label == null) throw new RuleNotFoundException(name);

        return label;
    }
}
