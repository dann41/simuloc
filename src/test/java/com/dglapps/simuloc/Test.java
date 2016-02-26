package com.dglapps.simuloc;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import com.dglapps.simuloc.executors.RealTimeRulesExecutor;
import com.dglapps.simuloc.listeners.ConsoleRulesExecutorListener;
import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.rules.Rule;
import com.dglapps.simuloc.rules.RulesBuilder;
import com.dglapps.simuloc.rules.SingleRouteRule;
import com.dglapps.simuloc.rules.StandstillRule;

public class Test {

	public static void main(String[] args) {
        Clock clock = Clock.systemUTC();
		ConsoleRulesExecutorListener listener = new ConsoleRulesExecutorListener(clock);
		
		Position p = getPosition();
        p.setTime(clock.millis());

        RulesBuilder builder = new RulesBuilder(p);
        builder.addStandstillRule(5000, 1000)
                .addSingleRouteRule(getPosition(2.555646, 38.45384), 20000, 1000);

		RealTimeRulesExecutor executor = new RealTimeRulesExecutor(clock);
		executor.addListener(listener);
		executor.execute(builder.build());
	}
	
	private static Position getPosition() {
		Position p = new Position();
		
		p.setLatitude(2.554646);
		p.setLongitude(38.45684);
		
		return p;
	}

    private static Position getPosition(double lat, double lng) {
        Position p = new Position();

        p.setLatitude(lat);
        p.setLongitude(lng);

        return p;
    }
}
