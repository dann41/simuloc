package com.dglapps.simuloc.infrastructure.repository;

import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.domain.trip.TripId;
import com.dglapps.simuloc.domain.trip.TripRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTripRepository implements TripRepository {

  private final Map<TripId, Trip> trips = new ConcurrentHashMap<>();

  @Override
  public Optional<Trip> findBy(TripId id) {
    return Optional.ofNullable(trips.get(id));
  }

  @Override
  public void save(Trip trip) {
    trips.put(trip.id(), trip);
  }

  @Override
  public Collection<Trip> findAll() {
    return trips.values();
  }
}
