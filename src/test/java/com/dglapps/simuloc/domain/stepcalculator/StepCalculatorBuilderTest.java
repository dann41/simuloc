package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.Position;
import com.dglapps.simuloc.domain.trip.TripBuilder;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static com.dglapps.simuloc.utils.CoordinatesMother.BCN;
import static com.dglapps.simuloc.utils.CoordinatesMother.MAD;
import static com.dglapps.simuloc.utils.DurationMother.TEN_SECONDS;
import static com.dglapps.simuloc.utils.OffsetDateTimeMother.FIRST_JULY_MIDNIGHT;
import static com.dglapps.simuloc.utils.PeriodMother.EVERY_FIVE_SECOND;
import static com.dglapps.simuloc.utils.PositionMother.BCN_POSITION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by dani on 26/2/16.
 */
public class StepCalculatorBuilderTest {

    @Test
    public void shouldAddStandstillStepCalculator() {
        TripBuilder builder = new TripBuilder(BCN, FIRST_JULY_MIDNIGHT);
        Duration duration = Duration.ofSeconds(5);
        Period period = Period.ofSeconds(1);

        List<StepCalculator> stepCalculators = builder.addStandstillStep(duration, period).build();

        assertThat(stepCalculators).hasSize(1);
        assertEquals(StandstillStepCalculator.class, stepCalculators.get(0).getClass());

        assertThat(stepCalculators.get(0)).isEqualTo(new StandstillStepCalculator(
                BCN_POSITION, duration, period
        ));

        Position lastPosition = BCN_POSITION.afterTime(duration);
        AssertUtils.assertEqualsPosition(lastPosition, stepCalculators.get(0).getLastPosition());
    }

    @Test
    public void testRulesBuilderAddSingleRoute() {
        TripBuilder builder = new TripBuilder(BCN, FIRST_JULY_MIDNIGHT);

        builder.addStraightStep(MAD, TEN_SECONDS, EVERY_FIVE_SECOND);

        List<StepCalculator> stepCalculators = builder.build();

        assertEquals(1, stepCalculators.size());
        assertEquals(StraightStepCalculator.class, stepCalculators.get(0).getClass());

        Position firstPosition = BCN_POSITION;
        Position lastPosition = Position.aBuilder(MAD)
                .withTime(firstPosition.time().plus(TEN_SECONDS))
                .build();

        AssertUtils.assertEqualsPosition(firstPosition, stepCalculators.get(0).getFirstPosition());
        AssertUtils.assertEqualsPosition(lastPosition, stepCalculators.get(0).getLastPosition());
    }

}
