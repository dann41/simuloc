package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.domain.trip.Step;

import java.time.OffsetDateTime;

public class StepCalculatorFactory {
  public StepCalculator stepCalculatorForStep(Step step, OffsetDateTime initialDateTime) {
    return switch (step.type()) {
      case STRAIGHT -> new StraightStepCalculator(step, initialDateTime);
      case STANDSTILL -> new StandstillStepCalculator(step, initialDateTime);
      default -> null;
    };
  }
}
