package com.dglapps.simuloc.domain.trip;

import java.time.Duration;

public record AddStepCommand(Coordinates destination,
                             Duration duration,
                             Period period,
                             StepType type) {
}
