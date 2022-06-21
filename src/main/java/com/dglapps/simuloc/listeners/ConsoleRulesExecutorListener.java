package com.dglapps.simuloc.listeners;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.executors.TripReplay;
import com.dglapps.simuloc.rules.StepCalculator;

import java.time.Clock;

public class ConsoleRulesExecutorListener implements RulesExecutorListener {

    private final Clock clock;
    private long start;

    public ConsoleRulesExecutorListener(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void onExecutorStart(TripReplay executor) {
        System.out.println("Executor started");
        start = System.currentTimeMillis();
    }

    @Override
    public void onRuleStart(TripReplay executor, StepCalculator stepCalculator) {
        System.out.println("Rule started");
    }

    @Override
    public void onPositionGenerated(TripReplay executor, DynamicPosition position) {
        long now = clock.millis();
        System.out.println(position.getLatitude() + "," + position.getLongitude() + "," + position.getTime() + " " + position
                .getSpeed() + " " + (now - position.getTime()));
    }

    @Override
    public void onRuleEnd(TripReplay executor, StepCalculator stepCalculator) {
        System.out.println("Rule ended");
    }

    @Override
    public void onExecutorEnd(TripReplay executor) {
        long end = clock.millis();
        System.out.println("Executor finished. Execution took " + (end - start) + " ms.");

    }
}
