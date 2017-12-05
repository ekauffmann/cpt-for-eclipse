package org.processmining.configurableprocesstree.parser.predicates;

public class TaskPredicate implements Predicate {
    @Override
    public boolean checkPredicate(String word) {
        return word != null && word.matches("\\d+");
    }
}
