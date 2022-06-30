package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.NotEmpty;

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

    public Coordinates firstPosition() {
        return step.stream().findFirst()
                .map(Step::firstPosition)
                .orElse(null);
    }

    public Coordinates lastPosition() {
        return step.stream().findFirst()
                .map(Step::firstPosition)
                .orElse(null);
    }

    public static class Builder {
        private Coordinates nextStepStartPosition;
        private final List<Step> steps;

        private Builder(Coordinates firstPosition) {
            this.nextStepStartPosition = firstPosition;
            this.steps = new ArrayList<>();
        }

        public static Builder aTrip(Coordinates firstPosition) {
            return new Builder(firstPosition);
        }

        public Builder withStandstillStep(Duration duration) {
            Step standstillStep = new Step(
                    nextStepStartPosition,
                    nextStepStartPosition,
                    duration,
                    Period.ofSeconds(1),
                    StepType.STANDSTILL
            );
            steps.add(standstillStep);
            return this;
        }

        public Builder withStraightStep(Coordinates endPosition, Duration duration) {
            Step straightStep = new Step(
                    nextStepStartPosition,
                    endPosition,
                    duration,
                    Period.ofSeconds(1),
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
