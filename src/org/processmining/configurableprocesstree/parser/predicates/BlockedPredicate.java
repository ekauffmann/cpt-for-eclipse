package org.processmining.configurableprocesstree.parser.predicates;

public class BlockedPredicate implements Predicate {
    @Override
    public boolean checkPredicate(String word) {
        return "B".equals(word);
    }
}
