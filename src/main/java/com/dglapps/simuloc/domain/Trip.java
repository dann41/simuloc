package com.dglapps.simuloc.domain;

import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.utils.NotEmpty;
import com.dglapps.simuloc.utils.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Trip {

    private final List<Step> step;

    public Trip(List<Step> steps) {
        NotEmpty.validate(steps, "steps");

        step = new ArrayList<>();
        step.addAll(steps);
    }

    public void addStep(Step step) {
        this.step.add(step);
    }

    public void removeStep(Step step) {
        this.step.remove(step);
    }

    public Position firstPosition() {
        return step.stream().findFirst()
                .map(Step::getFirstPosition)
                .orElse(null);
    }

    public Position lastPosition() {
        return step.stream().findFirst()
                .map(Step::getFirstPosition)
                .orElse(null);
    }

    public static class Builder {
        private Position nextStepStartPosition;
        private final List<Step> steps;

        private Builder(Position firstPosition) {
            this.nextStepStartPosition = firstPosition;
            this.steps = new ArrayList<>();
        }

        public static Builder aTrip(Position firstPosition) {
            return new Builder(firstPosition);
        }

        public Builder withStandstillStep(Duration duration) {
            Step standstillStep = new Step(
                    nextStepStartPosition,
                    nextStepStartPosition,
                    duration,
                    Frequency.ofPositionsPerMinute(60),
                    StepType.STANDSTILL
            );
            steps.add(standstillStep);
            return this;
        }

        public Builder withStraightStep(Position endPosition, Duration duration) {
            Step straightStep = new Step(
                    nextStepStartPosition,
                    endPosition,
                    duration,
                    Frequency.ofPositionsPerMinute(60),
                    StepType.STANDSTILL
            );
            steps.add(straightStep);
            nextStepStartPosition = endPosition;
            return this;
        }

        public Trip build() {
            return new Trip(steps);
        }

    }

}
