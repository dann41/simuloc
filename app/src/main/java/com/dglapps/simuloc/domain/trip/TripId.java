package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.NotEmpty;

import java.util.UUID;

public record TripId(String value) {

  public TripId {
    NotEmpty.validate(value, "TripId");
  }

  public static TripId random() {
    return new TripId(UUID.randomUUID().toString());
  }

}
