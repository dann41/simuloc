package com.dglapps.simuloc.infrastructure;

import com.dglapps.simuloc.domain.player.RealTimeTripPlayer;
import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.trip.Coordinates;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.TripBuilder;
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

        TripBuilder builder = new TripBuilder(startingCoordinates, OffsetDateTime.now(clock))
                .addStandstillStep(Duration.ofSeconds(20), Period.ofSeconds(1))
                .addStraightStep(endCoordinates, Duration.ofSeconds(30), Period.ofSeconds(4));

        RealTimeTripPlayer executor = new RealTimeTripPlayer(clock);
        executor.addListener(listener);
        executor.execute(builder.build());
    }

}
