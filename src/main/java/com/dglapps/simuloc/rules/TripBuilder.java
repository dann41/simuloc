package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dani on 26/2/16.
 */
public class TripBuilder {

    private final List<StepCalculator> stepCalculators = new LinkedList<>();
    private DynamicPosition lastPosition;


    public TripBuilder(DynamicPosition startingPosition) {
        this.lastPosition = startingPosition;
    }

    public TripBuilder addStandstillStep(long duration, long throughput) {
        StepCalculator stepCalculator = new StandstillStepCalculator(lastPosition, duration, throughput);
        stepCalculators.add(stepCalculator);
        this.lastPosition = stepCalculator.getLastPosition();

        return this;
    }

    /**
     * @param destination
     * @param duration
     * @param throughput
     * @return
     */
    public TripBuilder addStraightStep(DynamicPosition destination, long duration, long throughput) {
        // Generate a position with a proper time
        DynamicPosition dest = PositionFactory.cloneDynamicPosition(destination);
        dest.setTime(lastPosition.getTime() + duration);

        StepCalculator stepCalculator = new StraightStepCalculator(lastPosition, dest, duration, throughput);
        stepCalculators.add(stepCalculator);
        this.lastPosition = stepCalculator.getLastPosition();

        return this;
    }

    public List<StepCalculator> build() {
        return new LinkedList<>(stepCalculators);
    }
}
