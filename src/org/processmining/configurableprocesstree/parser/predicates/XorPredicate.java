package org.processmining.configurableprocesstree.parser.predicates;

public class XorPredicate implements Predicate {
    @Override
    public boolean checkPredicate(String word) {
        return "XOR".equals(word);
    }
}
