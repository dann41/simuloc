package com.dglapps.simuloc.application.stepadder.standstill;

import com.dglapps.simuloc.domain.trip.AddStepCommand;
import com.dglapps.simuloc.domain.trip.Coordinates;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.StepType;
import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.domain.trip.TripFinder;
import com.dglapps.simuloc.domain.trip.TripId;
import com.dglapps.simuloc.domain.trip.TripRepository;

import java.time.Duration;

public class StandstillStepAdder {

  private final TripRepository repository;
  private final TripFinder tripFinder;

  public StandstillStepAdder(TripFinder tripFinder, TripRepository repository) {
    this.tripFinder = tripFinder;
    this.repository = repository;
  }

  public void execute(StandstillStepAdderRequest request) {
    TripId id = new TripId(request.tripId());
    Trip trip = tripFinder.execute(id);

    trip.addStep(standstillStepCommand(trip.lastPosition(), request));

    repository.save(trip);
  }

  private AddStepCommand standstillStepCommand(Coordinates coordinates, StandstillStepAdderRequest request) {
    return new AddStepCommand(
        coordinates,
        Duration.ofSeconds(request.durationInSeconds()),
        Period.ofSeconds(request.periodInSecondsPerPosition()),
        StepType.STANDSTILL
    );
  }
}
