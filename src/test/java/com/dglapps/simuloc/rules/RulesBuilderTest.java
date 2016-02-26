package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by dani on 26/2/16.
 */
public class RulesBuilderTest {

    private RulesBuilder builder;

    @Test
    public void testRulesBuilderAddStandstill() {
        DynamicPosition firstPosition = PositionFactory.createDynamicPosition(0, 0, 0);
        long duration = 5000;
        long throughput = 1000;
        DynamicPosition lastPosition = PositionFactory.createDynamicPosition(0, 0, firstPosition.getTime() + duration);

        builder = new RulesBuilder(firstPosition);

        List<Rule> rules;

        rules = builder.build();
        assertTrue(rules.isEmpty());

        builder.addStandstillRule(duration, throughput);

        rules = builder.build();
        assertEquals(1, rules.size());
        assertEquals(StandstillRule.class, rules.get(0).getClass());
        AssertUtils.assertEqualsPosition(firstPosition, rules.get(0).getFirstPosition());
        AssertUtils.assertEqualsPosition(lastPosition, rules.get(0).getLastPosition());
    }

    @Test
    public void testRulesBuilderAddSingleRoute() {
        long initTime = 1000;
        DynamicPosition firstPosition = PositionFactory.createDynamicPosition(0, 0, initTime);
        long throughput = 1000;
        DynamicPosition lastPosition = PositionFactory.createDynamicPosition(0, 0, 213234);

        builder = new RulesBuilder(firstPosition);

        List<Rule> rules;

        rules = builder.build();
        assertTrue(rules.isEmpty());

        long duration = 100000;
        builder.addSingleRouteRule(lastPosition, duration, throughput);

        rules = builder.build();
        assertEquals(1, rules.size());
        assertEquals(SingleRouteRule.class, rules.get(0).getClass());

        lastPosition = PositionFactory.cloneDynamicPosition(lastPosition);
        lastPosition.setTime(initTime + duration);

        AssertUtils.assertEqualsPosition(firstPosition, rules.get(0).getFirstPosition());
        AssertUtils.assertEqualsPosition(lastPosition, rules.get(0).getLastPosition());
    }

}
