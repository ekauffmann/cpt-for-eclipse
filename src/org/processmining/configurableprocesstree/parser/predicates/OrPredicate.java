package org.processmining.configurableprocesstree.parser.predicates;

public class OrPredicate implements Predicate {
    @Override
    public boolean checkPredicate(String word) {
        return "OR".equals(word);
    }
}
