package org.processmining.configurableprocesstree.parser.predicates;

public class NoConfigurationPredicate implements Predicate {
    @Override
    public boolean checkPredicate(String word) {
        return "-".equals(word);
    }
}
