package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.domain.stepcalculator.StandstillStepCalculator;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;
import com.dglapps.simuloc.domain.stepcalculator.StraightStepCalculator;

import java.time.Clock;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dani on 26/2/16.
 */
public class TripBuilder {

    private final List<StepCalculator> stepCalculators = new LinkedList<>();
    private Position lastPosition;


    public TripBuilder(Coordinates startingCoordinates) {
        this(startingCoordinates, OffsetDateTime.now(Clock.systemUTC()));
    }

    public TripBuilder(Coordinates startingCoordinates, OffsetDateTime startingTime) {
        this.lastPosition = Position.aBuilder(startingCoordinates).withTime(startingTime).build();
    }

    public TripBuilder addStandstillStep(Duration duration, Period period) {
        StepCalculator stepCalculator = new StandstillStepCalculator(lastPosition, duration, period);
        stepCalculators.add(stepCalculator);
        this.lastPosition = stepCalculator.getLastPosition();

        return this;
    }

    public TripBuilder addStraightStep(Coordinates toCoordinates, Duration duration, Period period) {
        StepCalculator stepCalculator = new StraightStepCalculator(lastPosition, toCoordinates, duration, period);
        stepCalculators.add(stepCalculator);
        this.lastPosition = stepCalculator.getLastPosition();

        return this;
    }

    public List<StepCalculator> build() {
        return new LinkedList<>(stepCalculators);
    }
}
