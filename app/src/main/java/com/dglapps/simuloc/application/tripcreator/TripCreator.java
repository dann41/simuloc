package com.dglapps.simuloc.application.tripcreator;

import com.dglapps.simuloc.domain.trip.Coordinates;
import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.domain.trip.TripRepository;

public class TripCreator {

  private final TripRepository repository;

  public TripCreator(TripRepository repository) {
    this.repository = repository;
  }

  public void execute(TripCreatorRequest request) {
    Trip newTrip = Trip.Builder.aTrip(new Coordinates(request.latitude(), request.longitude()))
        .withId(request.tripId())
        .withName(request.name())
        .build();

    repository.save(newTrip);
  }

}
