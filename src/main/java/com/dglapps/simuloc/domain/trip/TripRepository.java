package com.dglapps.simuloc.domain.trip;

public interface TripRepository {

  Trip findBy(TripId id);

  void save(Trip trip);

}
