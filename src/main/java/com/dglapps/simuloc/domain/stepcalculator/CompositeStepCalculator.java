package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.domain.trip.Position;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dani on 2/3/16.
 */
public class CompositeStepCalculator implements StepCalculator {

    private final List<StepCalculator> stepCalculators;

    public CompositeStepCalculator(StepCalculator... stepCalculators) {
        this.stepCalculators = Arrays.asList(stepCalculators);
    }

    @Override
    public List<Position> generatePositions() {
        return stepCalculators.stream()
                .flatMap(step -> step.generatePositions().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Position getFirstPosition() {
        return stepCalculators.stream().findFirst()
                .map(StepCalculator::getFirstPosition)
                .orElse(null);
    }

    @Override
    public Position getLastPosition() {
        return stepCalculators.isEmpty()
                ? null
                : stepCalculators.get(stepCalculators.size() - 1).getLastPosition();
    }
}
