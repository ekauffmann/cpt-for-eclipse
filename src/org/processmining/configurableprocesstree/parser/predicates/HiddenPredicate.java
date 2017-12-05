package org.processmining.configurableprocesstree.parser.predicates;

public class HiddenPredicate implements Predicate {
    @Override
    public boolean checkPredicate(String word) {
        return "H".equals(word) || "TAU".equals(word);
    }
}
