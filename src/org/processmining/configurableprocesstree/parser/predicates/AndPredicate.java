package org.processmining.configurableprocesstree.parser.predicates;

public class AndPredicate implements Predicate {
    @Override
    public boolean checkPredicate(String word) {
        return "AND".equals(word);
    }
}
