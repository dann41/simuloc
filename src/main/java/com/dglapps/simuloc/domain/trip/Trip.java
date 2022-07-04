package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.NotNull;

import java.time.Duration;
import java.util.*;

public class Trip {

    private final Coordinates origin;
    private final List<Step> steps;

    public Trip(Coordinates origin, List<Step> steps) {
        NotNull.validate(origin, "origin cannot be null");
        NotNull.validate(steps, "steps");

        this.origin = origin;
        this.steps = new ArrayList<>();
        this.steps.addAll(steps);
    }

    public void addStep(AddStepCommand addStepCommand) {
        Step nextStep = lastStep()
                .map(lastStep -> Step.fromPreviousStep(lastStep, addStepCommand.destination(), addStepCommand.duration(), addStepCommand.period(), addStepCommand.type()))
                .orElse(Step.fromOrigin(origin, addStepCommand.destination(), addStepCommand.duration(), addStepCommand.period(), addStepCommand.type()));

        steps.add(nextStep);
    }

    public void removeStep(int position) {
        validateStepIndex(position);

        Step stepToRemove = steps.get(position);

        if (steps.size() - 1 < position) {
            int nextPosition = position + 1;
            Step nextStep = steps.get(nextPosition);
            Step updatedNextStep = nextStep.withFirstPosition(stepToRemove.firstPosition());
            steps.remove(nextStep);
            steps.add(nextPosition, updatedNextStep);
        }

        steps.remove(stepToRemove);
    }

    public void moveStep(int fromPosition, int toPosition) {
        validateStepIndex(fromPosition);
        validateStepIndex(toPosition);

        Step step = steps.remove(fromPosition);
        steps.add(toPosition, step);
    }

    public Coordinates firstPosition() {
        return origin;
    }

    public Coordinates lastPosition() {
        return lastStep()
                .map(Step::lastPosition)
                .orElse(null);
    }

    private Optional<Step> lastStep() {
        if (steps.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(steps.get(steps.size() - 1));
    }

    private void validateStepIndex(int fromPosition) {
        if (steps.size() <= fromPosition) {
            throw new IllegalArgumentException("There are no step at position " + fromPosition);
        }
    }

    public Collection<Step> steps() {
        return Collections.unmodifiableList(steps);
    }

    public static class Builder {

        private final Coordinates firstPosition;
        private Coordinates nextStepStartPosition;
        private final List<Step> steps;

        private Builder(Coordinates firstPosition) {
            this.firstPosition = firstPosition;
            this.nextStepStartPosition = firstPosition;
            this.steps = new ArrayList<>();
        }

        public static Builder aTrip(Coordinates firstPosition) {
            return new Builder(firstPosition);
        }

        public Builder withStandstillStep(Duration duration, Period period) {
            Step standstillStep = new Step(
                    nextStepStartPosition,
                    nextStepStartPosition,
                    duration,
                    period,
                    StepType.STANDSTILL
            );
            steps.add(standstillStep);
            return this;
        }

        public Builder withStraightStep(Coordinates endPosition, Duration duration, Period period) {
            Step straightStep = new Step(
                    nextStepStartPosition,
                    endPosition,
                    duration,
                    period,
                    StepType.STRAIGHT
            );
            steps.add(straightStep);
            nextStepStartPosition = endPosition;
            return this;
        }

        public Trip build() {
            return new Trip(firstPosition, steps);
        }

    }

}
