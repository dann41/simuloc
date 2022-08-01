package com.dglapps.simuloc.application.triprename;

import com.dglapps.simuloc.domain.trip.Name;
import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.domain.trip.TripFinder;
import com.dglapps.simuloc.domain.trip.TripId;
import com.dglapps.simuloc.domain.trip.TripRepository;

public class TripRenamer {

  private final TripRepository repository;
  private final TripFinder tripFinder;

  public TripRenamer(TripFinder tripFinder, TripRepository repository) {
    this.tripFinder = tripFinder;
    this.repository = repository;
  }

  public void execute(TripRenamerRequest request) {
    TripId id = new TripId(request.tripId());
    Trip trip = tripFinder.execute(id);

    trip.rename(new Name(request.newName()));

    repository.save(trip);
  }

}
