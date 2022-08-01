package com.dglapps.simuloc.domain.trip;

public class TripNotFound extends RuntimeException {
  public TripNotFound(TripId id) {
    super("Trip with id " + id + " not found");
  }
}
