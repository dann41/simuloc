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
public class CompositeStepCalculator implements StepCalculator {

    private final StepCalculator[] stepCalculators;

    public CompositeStepCalculator(StepCalculator... stepCalculators) {
        this.stepCalculators = stepCalculators;
    }

    @Override
    public List<DynamicPosition> generatePositions() {
        List<DynamicPosition> positions = new ArrayList<>();
        for (StepCalculator stepCalculator : stepCalculators) {
            positions.addAll(stepCalculator.generatePositions());
        }
        return positions;
    }

    @Override
    public DynamicPosition getFirstPosition() {
        return stepCalculators.length > 0 ? stepCalculators[0].getFirstPosition() : null;
    }

    @Override
    public DynamicPosition getLastPosition() {
        return stepCalculators.length > 0 ? stepCalculators[stepCalculators.length - 1].getLastPosition() : null;
    }

    @Override
    public Iterator<DynamicPosition> iterator() {
        List<Iterator<DynamicPosition>> iterators = new LinkedList<>();
        for (StepCalculator stepCalculator : stepCalculators) {
            iterators.add(stepCalculator.iterator());
        }

        return Iterators.concat(iterators.toArray(new Iterator[iterators.size()]));
    }
}
