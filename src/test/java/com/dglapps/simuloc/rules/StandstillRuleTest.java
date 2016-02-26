package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by dani on 26/2/16.
 */
public class StandstillRuleTest {

    private StandstillRule rule;

    @Test
    public void testStandstillRuleTwoPositionsWhenThroughputGreaterThanDuration() {
        DynamicPosition p = PositionFactory.createDynamicPosition(1.6, 2.6, 1000);
        long duration = 10000;
        long throughput = 200000;

        rule = new StandstillRule(p, duration, throughput);

        List<DynamicPosition> result = rule.generatePositions();
        assertEquals(2, result.size());

        DynamicPosition p1 = result.get(0);
        AssertUtils.assertEqualsPosition(p, p1);

        DynamicPosition p2 = result.get(1);
        DynamicPosition expectedP2 = PositionFactory.createDynamicPosition(p.getLatitude(), p.getLongitude(), p.getTime() + duration);
        AssertUtils.assertEqualsPosition(expectedP2, p2);

        AssertUtils.assertTimeIncreasing(result);
    }

    @Test
    public void testStandstillRuleRegularBehavior() {
        DynamicPosition p = PositionFactory.createDynamicPosition(1.6, 2.6, 1000);
        long duration = 10000;
        long throughput = 2000;

        rule = new StandstillRule(p, duration, throughput);

        List<DynamicPosition> result = rule.generatePositions();
        assertEquals(2 + 5, result.size());

        AssertUtils.assertTimeIncreasing(result);
    }

    @Test
    public void testStandstillRuleWithInexactPositions() {
        DynamicPosition p = PositionFactory.createDynamicPosition(1.6, 2.6, 1000);
        long duration = 10000;
        long throughput = 3000;

        rule = new StandstillRule(p, duration, throughput);

        List<DynamicPosition> result = rule.generatePositions();
        assertEquals(2 + 3, result.size());

        AssertUtils.assertTimeIncreasing(result);
    }

    @Test
    public void testFirstPosition() {
        DynamicPosition p = PositionFactory.createDynamicPosition(1.6, 2.6, 1000);
        long duration = 10000;
        long throughput = 200000;

        rule = new StandstillRule(p, duration, throughput);

        AssertUtils.assertEqualsPosition(p, rule.getFirstPosition());
    }

    @Test
    public void testLastPosition() {
        DynamicPosition p = PositionFactory.createDynamicPosition(1.6, 2.6, 1000);
        long duration = 10000;
        long throughput = 200000;

        rule = new StandstillRule(p, duration, throughput);

        DynamicPosition expected = PositionFactory.createDynamicPosition(p.getLatitude(), p.getLongitude(), p.getTime() + duration);

        AssertUtils.assertEqualsPosition(expected, rule.getLastPosition());
    }

}
