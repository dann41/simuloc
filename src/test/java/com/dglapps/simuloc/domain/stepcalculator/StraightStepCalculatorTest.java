package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.Position;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static com.dglapps.simuloc.utils.CoordinatesMother.MAD;
import static com.dglapps.simuloc.utils.DurationMother.ONE_SECOND;
import static com.dglapps.simuloc.utils.DurationMother.TEN_SECONDS;
import static com.dglapps.simuloc.utils.PositionMother.BCN_POSITION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StraightStepCalculatorTest {

    @Test
    public void testThroughputGreaterThanDurationGenerates2Positions() {
        Period period = Period.ofSeconds(5);
        Position src = BCN_POSITION;
        StraightStepCalculator stepCalculator = new StraightStepCalculator(src, MAD, ONE_SECOND, period);

        List<Position> result = stepCalculator.generatePositions();

        assertNotNull(result);
        assertEquals(2, result.size());
        AssertUtils.assertTimeIncreasing(result);
    }

    @Test
    public void testGenerates3Positions() {
        Period period = Period.ofSeconds(4);
        Position src = BCN_POSITION;

        StraightStepCalculator stepCalculator = new StraightStepCalculator(src, MAD, TEN_SECONDS, period);

        List<Position> result = stepCalculator.generatePositions();
        assertNotNull(result);
        assertEquals(4, result.size());
        AssertUtils.assertTimeIncreasing(result);
    }

    @Test
    public void testFirstPosition() {
        Period period = Period.ofSeconds(5);
        Position src = BCN_POSITION;

        StraightStepCalculator stepCalculator = new StraightStepCalculator(src, MAD, TEN_SECONDS, period);

        AssertUtils.assertEqualsPosition(src, stepCalculator.getFirstPosition());
    }

    @Test
    public void testLastPosition() {
        Period period = Period.ofSeconds(5);
        Position src = BCN_POSITION;

        StraightStepCalculator stepCalculator = new StraightStepCalculator(src, MAD, ONE_SECOND, period);

        assertThat(stepCalculator.getLastPosition().coordinates()).isEqualTo(MAD);
    }
}
