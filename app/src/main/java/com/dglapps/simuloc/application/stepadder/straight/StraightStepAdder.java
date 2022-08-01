package com.dglapps.simuloc.application.stepadder.straight;

import com.dglapps.simuloc.domain.trip.AddStepCommand;
import com.dglapps.simuloc.domain.trip.Coordinates;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.StepType;
import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.domain.trip.TripFinder;
import com.dglapps.simuloc.domain.trip.TripId;
import com.dglapps.simuloc.domain.trip.TripRepository;

import java.time.Duration;

public class StraightStepAdder {

  private final TripRepository repository;
  private final TripFinder tripFinder;

  public StraightStepAdder(TripFinder tripFinder, TripRepository repository) {
    this.tripFinder = tripFinder;
    this.repository = repository;
  }

  public void execute(StraightStepAdderRequest request) {
    TripId id = new TripId(request.tripId());
    Trip trip = tripFinder.execute(id);

    trip.addStep(standstillStepCommand(request));

    repository.save(trip);
  }

  private AddStepCommand standstillStepCommand(StraightStepAdderRequest request) {
    return new AddStepCommand(
        new Coordinates(request.latitude(), request.longitude()),
        Duration.ofSeconds(request.durationInSeconds()),
        Period.ofSeconds(request.periodInSecondsPerPosition()),
        StepType.STRAIGHT
    );
  }
}
