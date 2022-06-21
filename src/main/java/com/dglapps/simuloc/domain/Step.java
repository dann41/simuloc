package com.dglapps.simuloc.domain;

import com.dglapps.simuloc.entities.Position;

import java.time.Duration;

public class Step {
    private final Position firstPosition;
    private final Position lastPosition;
    private final Duration duration;
    private final Frequency frequency;
    private final StepType type;

    public Step(Position firstPosition, Position lastPosition, Duration duration, Frequency frequency, StepType type) {
        this.firstPosition = firstPosition;
        this.lastPosition = lastPosition;
        this.duration = duration;
        this.frequency = frequency;
        this.type = type;
    }

    public Position getFirstPosition() {
        return firstPosition;
    }

    public Position getLastPosition() {
        return lastPosition;
    }

    public Duration getDuration() {
        return duration;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public StepType getType() {
        return type;
    }
}
