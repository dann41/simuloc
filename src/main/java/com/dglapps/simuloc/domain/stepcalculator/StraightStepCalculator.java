package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.domain.trip.Coordinates;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.Position;
import com.dglapps.simuloc.domain.trip.Step;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StraightStepCalculator implements StepCalculator {

    private final Position source;
    private final Position destination;
    private final Duration duration;
    private final Period period;

    public StraightStepCalculator(Step step, OffsetDateTime initialDateTime) {
        this(
                Position.aBuilder(step.firstPosition()).withTime(initialDateTime).build(),
                step.lastPosition(),
                step.duration(),
                step.period()
        );
    }

    public StraightStepCalculator(Position source, Coordinates destination, Duration duration, Period period) {
        this.source = source;
        this.destination = Position.aBuilder(destination)
                .withTime(source.time().plus(duration))
                .build();
        this.duration = duration;
        this.period = period;
    }

    @Override
    public Position getFirstPosition() {
        return source;
    }

    @Override
    public Position getLastPosition() {
        return destination;
    }

    @Override
    public List<Position> generatePositions() {
        final List<Position> positions = new LinkedList<>();
        for (Iterator<Position> it = iterator(); it.hasNext(); ) {
            Position p = it.next();
            positions.add(p);
        }

        return positions;
    }

    private Iterator<Position> iterator() {
        return new PositionIterator();
    }

    private class PositionIterator implements Iterator<Position> {

        private final int numPositions;
        private final double deltaLat;
        private final double deltaLng;
        private final Duration deltaTime;
        private final double speed;

        private Position lastPosition;
        private int generatedPositions;

        public PositionIterator() {
            this.numPositions = (int) Math.floor(duration.toMillis() / period.value().toMillis());

            if (numPositions != 0) {
                this.deltaLat = (destination.latitude() - source.latitude()) / numPositions;
                this.deltaLng = (destination.longitude() - source.longitude()) / numPositions;
                this.deltaTime = period.value();
                this.speed = 1000 * DistanceCalculator.getDistance(source.coordinates(), destination.coordinates())
                        / (double) duration.toSeconds();
            } else {
                this.deltaLat = 0;
                this.deltaLng = 0;
                this.deltaTime = Duration.ZERO;
                this.speed = 0;
            }

            this.generatedPositions = 0;
        }

        @Override
        public boolean hasNext() {
            return generatedPositions < numPositions + 2;
        }

        @Override
        public Position next() {
            if (lastPosition == null) {
                lastPosition = source;
            } else if (generatedPositions == numPositions + 1) {
                lastPosition = destination;
            } else {
                lastPosition = generateNextPosition(lastPosition, deltaLat, deltaLng, deltaTime, speed);
            }
            generatedPositions++;
            return lastPosition;
        }

        private Position generateNextPosition(Position position, double deltaLat, double deltaLng, Duration deltaTime, double speed) {
            return position.afterMoving(deltaLat, deltaLng, deltaTime, speed);
        }
    }
}
