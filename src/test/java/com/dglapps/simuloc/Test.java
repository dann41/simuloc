package com.dglapps.simuloc;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import com.dglapps.simuloc.executors.RealTimeTripReplay;
import com.dglapps.simuloc.listeners.ConsoleRulesExecutorListener;
import com.dglapps.simuloc.rules.TripBuilder;

import java.time.Clock;

public class Test {

    public static void main(String[] args) {
        Clock clock = Clock.systemUTC();
        ConsoleRulesExecutorListener listener = new ConsoleRulesExecutorListener(clock);

        DynamicPosition p = getPosition(clock.millis());

        TripBuilder builder = new TripBuilder(p);
        builder.addStandstillStep(5000, 1000)
                .addStraightStep(getPosition(2.555646, 38.45384), 20000, 1000);

        RealTimeTripReplay executor = new RealTimeTripReplay(clock);
        executor.addListener(listener);
        executor.execute(builder.build());
    }

    private static DynamicPosition getPosition(long time) {
        return PositionFactory.createDynamicPosition(2.554646, 38.45684, time);
    }

    private static DynamicPosition getPosition(double lat, double lng) {
        return PositionFactory.createDynamicPosition(lat, lng, 0);
    }
}
