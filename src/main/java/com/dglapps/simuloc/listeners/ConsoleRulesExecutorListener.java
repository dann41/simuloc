package com.dglapps.simuloc.listeners;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.executors.RulesExecutor;
import com.dglapps.simuloc.rules.Rule;

import java.time.Clock;

public class ConsoleRulesExecutorListener implements RulesExecutorListener {

    private final Clock clock;
    private long start;

    public ConsoleRulesExecutorListener(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void onExecutorStart(RulesExecutor executor) {
        System.out.println("Executor started");
        start = System.currentTimeMillis();
    }

    @Override
    public void onRuleStart(RulesExecutor executor, Rule rule) {
        System.out.println("Rule started");
    }

    @Override
    public void onPositionGenerated(RulesExecutor executor, DynamicPosition position) {
        long now = clock.millis();
        System.out.println(position.getLatitude() + "," + position.getLongitude() + "," + position.getTime() + " " + position
                .getSpeed() + " " + (now - position.getTime()));
    }

    @Override
    public void onRuleEnd(RulesExecutor executor, Rule rule) {
        System.out.println("Rule ended");
    }

    @Override
    public void onExecutorEnd(RulesExecutor executor) {
        long end = clock.millis();
        System.out.println("Executor finished. Execution took " + (end - start) + " ms.");

    }
}
