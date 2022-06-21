package com.dglapps.simuloc.executors;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.Position;

import java.time.Clock;

/**
 * Created by dani on 25/2/16.
 */
public class RealTimeTripReplay extends BaseTripReplay {

    private final Clock clock;
    private Position previousPosition;

    public RealTimeTripReplay(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void execute(DynamicPosition position) {
        if (position != null) {
            if (previousPosition == null) {
                previousPosition = position;
            } else {
                long timeToSleep = position.getTime() - clock.millis();
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
