package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by dani on 26/2/16.
 */
public class StraightStepTestCalculator {

    private StraightStepCalculator rule;

    @Test
    public void testThroughputGreaterThanDurationGenerates2Positions() {
        long duration = 1000;
        long throughput = 5000;
        DynamicPosition src = PositionFactory.createDynamicPosition(0.0, 0.0, 0);
        DynamicPosition dest = PositionFactory.createDynamicPosition(0.0, 0.0, 1000);

        rule = new StraightStepCalculator(src, dest, duration, throughput);

        List<DynamicPosition> result = rule.generatePositions();
        assertNotNull(result);
        assertEquals(2, result.size());
        AssertUtils.assertTimeIncreasing(result);
    }

    @Test
    public void testGenerates3Positions() {
        long duration = 10000;
        long throughput = 4000;
        DynamicPosition src = PositionFactory.createDynamicPosition(0.0, 0.0, 0);
        DynamicPosition dest = PositionFactory.createDynamicPosition(0.0, 0.0, 5000);

        rule = new StraightStepCalculator(src, dest, duration, throughput);

        List<DynamicPosition> result = rule.generatePositions();
        assertNotNull(result);
        assertEquals(4, result.size());
        AssertUtils.assertTimeIncreasing(result);
    }

    @Test
    public void testFirstPosition() {
        long duration = 1000;
        long throughput = 5000;
        DynamicPosition src = PositionFactory.createDynamicPosition(0.0, 0.0, 0);
        DynamicPosition dest = PositionFactory.createDynamicPosition(0.0, 0.0, 1000);

        rule = new StraightStepCalculator(src, dest, duration, throughput);

        AssertUtils.assertEqualsPosition(src, rule.getFirstPosition());
    }

    @Test
    public void testLastPosition() {
        long duration = 1000;
        long throughput = 5000;
        DynamicPosition src = PositionFactory.createDynamicPosition(0.0, 0.0, 0);
        DynamicPosition dest = PositionFactory.createDynamicPosition(0.0, 0.0, 1000);

        rule = new StraightStepCalculator(src, dest, duration, throughput);

        AssertUtils.assertEqualsPosition(dest, rule.getLastPosition());
    }
}
