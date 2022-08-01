package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.domain.trip.TripId;
import com.dglapps.simuloc.domain.trip.TripNotFound;
import com.dglapps.simuloc.domain.trip.TripRepository;

public class TripFinder {

  private final TripRepository repository;

  public TripFinder(TripRepository repository) {
    this.repository = repository;
  }

  public Trip execute(TripId tripId) {
    return repository.findBy(tripId)
        .orElseThrow(() -> new TripNotFound(tripId));
  }
}
