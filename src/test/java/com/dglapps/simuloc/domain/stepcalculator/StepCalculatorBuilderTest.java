package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.Position;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.dglapps.simuloc.utils.CoordinatesMother.MAD;
import static com.dglapps.simuloc.utils.DurationMother.TEN_SECONDS;
import static com.dglapps.simuloc.utils.PeriodMother.EVERY_FIVE_SECOND;
import static com.dglapps.simuloc.utils.PositionMother.BCN_POSITION;

/**
 * Created by dani on 26/2/16.
 */
public class StepCalculatorBuilderTest {

    @Test
    public void shouldAddStandstillStepCalculator() {
        Duration duration = Duration.ofSeconds(5);
        Period period = Period.ofSeconds(1);

        StandstillStepCalculator stepCalculator = new StandstillStepCalculator(BCN_POSITION, duration, period);

        Position lastPosition = BCN_POSITION.afterTime(duration);
        AssertUtils.assertEqualsPosition(lastPosition, stepCalculator.getLastPosition());
    }

    @Test
    public void testRulesBuilderAddSingleRoute() {
        StraightStepCalculator stepCalculator = new StraightStepCalculator(BCN_POSITION, MAD, TEN_SECONDS, EVERY_FIVE_SECOND);

        Position firstPosition = BCN_POSITION;
        Position lastPosition = Position.aBuilder(MAD)
                .withTime(firstPosition.time().plus(TEN_SECONDS))
                .build();

        AssertUtils.assertEqualsPosition(firstPosition, stepCalculator.getFirstPosition());
        AssertUtils.assertEqualsPosition(lastPosition, stepCalculator.getLastPosition());
    }

}
