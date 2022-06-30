package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.trip.Position;

import java.time.Clock;

/**
 * Created by dani on 25/2/16.
 */
public class RealTimeTripPlayer extends BaseTripPlayer {

    private final Clock clock;
    private Position previousPosition;

    public RealTimeTripPlayer(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void execute(Position position) {
        if (position != null) {
            if (previousPosition == null) {
                previousPosition = position;
            } else {
                long timeToSleep = position.time().toEpochSecond()*1000 - clock.millis();
                if (timeToSleep > 0) {
                    delay(timeToSleep);
                }
            }
        }
    }

    protected void delay(long delay) {
        try {
            Thread.sleep( delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
