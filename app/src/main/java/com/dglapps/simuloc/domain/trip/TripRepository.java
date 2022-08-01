package com.dglapps.simuloc.domain.trip;

import java.util.Collection;
import java.util.Optional;

public interface TripRepository {

  Optional<Trip> findBy(TripId id);

  void save(Trip trip);

  Collection<Trip> findAll();
}
