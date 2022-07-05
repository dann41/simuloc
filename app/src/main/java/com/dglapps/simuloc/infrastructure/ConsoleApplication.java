package com.dglapps.simuloc.infrastructure;

import com.dglapps.simuloc.domain.player.TripPlayer;
import com.dglapps.simuloc.domain.player.RealTimePositionPlayer;
import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculatorFactory;
import com.dglapps.simuloc.domain.trip.Coordinates;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.infrastructure.player.listener.ConsoleTripPlayerListener;

import java.time.Clock;
import java.time.Duration;
import java.time.OffsetDateTime;

public class ConsoleApplication {

  public static void main(String[] args) {
    Clock clock = Clock.systemUTC();
    TripPlayerListener listener = new ConsoleTripPlayerListener(clock);

    Coordinates startingCoordinates = new Coordinates(2.554646, 38.45684);
    Coordinates endCoordinates = new Coordinates(3.555646, 35.45384);

    Trip trip = Trip.Builder.aTrip(startingCoordinates)
        .withStandstillStep(Duration.ofSeconds(20), Period.ofSeconds(1))
        .withStraightStep(endCoordinates, Duration.ofSeconds(30), Period.ofSeconds(4))
        .build();

    TripPlayer player = new TripPlayer(new StepCalculatorFactory(), new RealTimePositionPlayer(clock));
    player.addListener(listener);
    player.play(trip, OffsetDateTime.now(clock));
  }

}
