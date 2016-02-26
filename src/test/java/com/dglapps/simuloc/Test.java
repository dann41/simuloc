package com.dglapps.simuloc;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import com.dglapps.simuloc.executors.RealTimeRulesExecutor;
import com.dglapps.simuloc.listeners.ConsoleRulesExecutorListener;
import com.dglapps.simuloc.rules.RulesBuilder;

import java.time.Clock;

public class Test {

    public static void main(String[] args) {
        Clock clock = Clock.systemUTC();
        ConsoleRulesExecutorListener listener = new ConsoleRulesExecutorListener(clock);

        DynamicPosition p = getPosition(clock.millis());

        RulesBuilder builder = new RulesBuilder(p);
        builder.addStandstillRule(5000, 1000)
                .addSingleRouteRule(getPosition(2.555646, 38.45384), 20000, 1000);

        RealTimeRulesExecutor executor = new RealTimeRulesExecutor(clock);
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
