package com.dglapps.simuloc.application.tripsfinder;

import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.domain.trip.TripRepository;

import java.util.Collection;

public class AllTripsFinder {

  private final TripRepository repository;

  public AllTripsFinder(TripRepository repository) {
    this.repository = repository;
  }

  public Collection<Trip> execute() {
    return repository.findAll();
  }
}
