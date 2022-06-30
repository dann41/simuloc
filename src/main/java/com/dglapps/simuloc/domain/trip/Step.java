package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.NotNull;

import java.time.Duration;

public record Step(
        Coordinates firstPosition,
        Coordinates lastPosition,
        Duration duration,
        Period period,
        StepType type) {

    public Step {
        NotNull.validate(firstPosition, "First position cannot be null");
        NotNull.validate(lastPosition, "Last position cannot be null");
        NotNull.validate(duration, "Duration cannot be null");
        NotNull.validate(period, "Period cannot be null");
        NotNull.validate(type, "Type cannot be null");
    }
}
