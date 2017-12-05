package org.processmining.configurableprocesstree.parser.predicates;

public class LoopPredicate implements Predicate {
    @Override
    public boolean checkPredicate(String word) {
        return "LOOP".equals(word);
    }
}
