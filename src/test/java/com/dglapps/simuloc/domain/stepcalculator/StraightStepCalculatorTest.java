package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.domain.stepcalculator.StraightStepCalculator;
import com.dglapps.simuloc.domain.trip.Coordinates;
import com.dglapps.simuloc.domain.trip.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StraightStepCalculatorTest {

    private StraightStepCalculator rule;

    @Test
    public void testThroughputGreaterThanDurationGenerates2Positions() {
        /*long duration = 1000;
        long throughput = 5000;
        Position src = Position.aBuilder(new Coordinates(0.0, 0.0)).build();
        Position dest = Position.aBuilder(new Coordinates(0.0, 0.0)).withTime(1000).build();

        rule = new StraightStepCalculator(src, dest, duration, throughput);

        List<Position> result = rule.generatePositions();
        assertNotNull(result);
        assertEquals(2, result.size());
        AssertUtils.assertTimeIncreasing(result);*/
    }

    @Test
    public void testGenerates3Positions() {
        /*long duration = 10000;
        long throughput = 4000;
        Position src = Position.aBuilder(new Coordinates(0.0, 0.0)).build();
        Position dest = Position.aBuilder(new Coordinates(0.0, 0.0)).withTime(5000).build();

        rule = new StraightStepCalculator(src, dest, duration, throughput);

        List<Position> result = rule.generatePositions();
        assertNotNull(result);
        assertEquals(4, result.size());
        AssertUtils.assertTimeIncreasing(result);*/
    }

    @Test
    public void testFirstPosition() {
        /*long duration = 1000;
        long throughput = 5000;
        Position src = Position.aBuilder(new Coordinates(0.0, 0.0)).build();
        Position dest = Position.aBuilder(new Coordinates(0.0, 0.0)).withTime(1000).build();

        rule = new StraightStepCalculator(src, dest, duration, throughput);

        AssertUtils.assertEqualsPosition(src, rule.getFirstPosition());*/
    }

    @Test
    public void testLastPosition() {
        /*long duration = 1000;
        long throughput = 5000;
        Position src = Position.aBuilder(new Coordinates(0.0, 0.0)).build();
        Position dest = Position.aBuilder(new Coordinates(0.0, 0.0)).withTime(1000).build();

        rule = new StraightStepCalculator(src, dest, duration, throughput);

        AssertUtils.assertEqualsPosition(dest, rule.getLastPosition());*/
    }
}
