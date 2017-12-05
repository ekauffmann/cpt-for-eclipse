package org.processmining.configurableprocesstree.exceptions;

public class RuleNotFoundException extends Exception {
    private String ruleName;

    public RuleNotFoundException(String ruleName) {
        super("No rule was found for parsing '" + ruleName + "'");
        this.ruleName = ruleName;
    }

    public String getRuleName() {
        return ruleName;
    }

}
