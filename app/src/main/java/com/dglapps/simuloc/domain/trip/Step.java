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

  public Step withFirstPosition(Coordinates firstPosition) {
    return new Step(
        firstPosition,
        lastPosition,
        duration,
        period,
        type
    );
  }

  public static Step fromOrigin(Coordinates origin, Coordinates destination, Duration duration, Period period,
                                StepType stepType) {
    return new Step(
        origin,
        destination,
        duration,
        period,
        stepType
    );
  }

  public static Step fromPreviousStep(Step previousStep, Coordinates destination, Duration duration, Period period,
                                      StepType stepType) {
    return new Step(
        previousStep.lastPosition,
        destination,
        duration,
        period,
        stepType
    );
  }
}
