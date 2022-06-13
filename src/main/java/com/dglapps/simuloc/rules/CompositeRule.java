package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.utils.Iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dani on 2/3/16.
 */
public class CompositeRule implements Rule {

    private final Rule[] rules;

    public CompositeRule(Rule... rules) {
        this.rules = rules;
    }

    @Override
    public List<DynamicPosition> generatePositions() {
        List<DynamicPosition> positions = new ArrayList<>();
        for (Rule rule : rules) {
            positions.addAll(rule.generatePositions());
        }
        return positions;
    }

    @Override
    public DynamicPosition getFirstPosition() {
        return rules.length > 0 ? rules[0].getFirstPosition() : null;
    }

    @Override
    public DynamicPosition getLastPosition() {
        return rules.length > 0 ? rules[rules.length - 1].getLastPosition() : null;
    }

    @Override
    public Iterator<DynamicPosition> iterator() {
        List<Iterator<DynamicPosition>> iterators = new LinkedList<>();
        for (Rule rule : rules) {
            iterators.add(rule.iterator());
        }

        return Iterators.concat(iterators.toArray(new Iterator[iterators.size()]));
    }
}
