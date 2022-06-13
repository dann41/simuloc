package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dani on 26/2/16.
 */
public class RulesBuilder {

    private final List<Rule> rules = new LinkedList<>();
    private DynamicPosition lastPosition;


    public RulesBuilder(DynamicPosition startingPosition) {
        this.lastPosition = startingPosition;
    }

    public RulesBuilder addStandstillRule(long duration, long throughput) {
        Rule rule = new StandstillRule(lastPosition, duration, throughput);
        rules.add(rule);
        this.lastPosition = rule.getLastPosition();

        return this;
    }

    /**
     * @param destination
     * @param duration
     * @param throughput
     * @return
     */
    public RulesBuilder addSingleRouteRule(DynamicPosition destination, long duration, long throughput) {
        // Generate a position with a proper time
        DynamicPosition dest = PositionFactory.cloneDynamicPosition(destination);
        dest.setTime(lastPosition.getTime() + duration);

        Rule rule = new SingleRouteRule(lastPosition, dest, duration, throughput);
        rules.add(rule);
        this.lastPosition = rule.getLastPosition();

        return this;
    }

    public List<Rule> build() {
        return new LinkedList<>(rules);
    }
}
