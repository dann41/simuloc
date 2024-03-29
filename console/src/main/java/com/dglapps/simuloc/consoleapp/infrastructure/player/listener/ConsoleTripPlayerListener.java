package com.dglapps.simuloc.consoleapp.infrastructure.player.listener;

import com.dglapps.simuloc.domain.player.TripPlayer;
import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;
import com.dglapps.simuloc.domain.trip.Position;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class ConsoleTripPlayerListener implements TripPlayerListener {

  private final Clock clock;
  private long start;

  public ConsoleTripPlayerListener(Clock clock) {
    this.clock = clock;
  }

  @Override
  public void onTripPlayerStart(TripPlayer tripPlayer) {
    System.out.println("Executor started");
    start = System.currentTimeMillis();
  }

  @Override
  public void onRuleStart(TripPlayer tripPlayer, StepCalculator stepCalculator) {
    System.out.println("Rule started");
  }

  @Override
  public void onPositionGenerated(TripPlayer tripPlayer, Position position) {
    System.out.println(position.latitude() + "," + position.longitude() + "," + position.time() + " " + position
        .speed() + " " + ChronoUnit.SECONDS.between(position.time(), OffsetDateTime.now()));
  }

  @Override
  public void onRuleEnd(TripPlayer executor, StepCalculator stepCalculator) {
    System.out.println("Rule ended");
  }

  @Override
  public void onTripPlayerEnd(TripPlayer tripPlayer) {
    long end = clock.millis();
    System.out.println("Executor finished. Execution took " + (end - start) + " ms.");

  }
}
