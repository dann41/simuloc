package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.PositiveNumber;

import java.time.Duration;

/**
 * Time between positions
 *
 * @param value
 */
public record Period(Duration value) {

  public Period {
    PositiveNumber.validate(value.toMillis(), "period");
  }

  public static Period ofSeconds(long seconds) {
    return new Period(Duration.ofSeconds(seconds));
  }

}
