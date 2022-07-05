package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.NotEmpty;

public record TripId(String value) {

  public TripId {
    NotEmpty.validate(value, "TripId");
  }

}
