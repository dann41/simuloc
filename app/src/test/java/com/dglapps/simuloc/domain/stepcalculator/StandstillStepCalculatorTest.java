package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dglapps.simuloc.utils.DurationMother.TEN_SECONDS;
import static com.dglapps.simuloc.utils.PositionMother.BCN_POSITION;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandstillStepCalculatorTest {

  @Test
  public void givenPeriodHigherThanDurationWhenGeneratePositionsThenReturnTwoPositions() {
    Position startPosition = BCN_POSITION;
    Period period = Period.ofSeconds(15);
    StepCalculator stepCalculator = new StandstillStepCalculator(startPosition, TEN_SECONDS, period);

    List<Position> result = stepCalculator.generatePositions();

    assertEquals(2, result.size());
    Position firstPosition = result.get(0);
    AssertUtils.assertEqualsPosition(startPosition, firstPosition);
    Position secondPosition = result.get(1);
    Position expectedSecondPosition = Position.aBuilder(startPosition.coordinates())
        .withTime(startPosition.time().plus(TEN_SECONDS))
        .build();
    AssertUtils.assertEqualsPosition(expectedSecondPosition, secondPosition);

    AssertUtils.assertTimeIncreasing(result);
  }

  @Test
  public void testStandstillRuleRegularBehavior() {
    StepCalculator stepCalculator = new StandstillStepCalculator(BCN_POSITION, TEN_SECONDS, Period.ofSeconds(2));

    List<Position> result = stepCalculator.generatePositions();

    assertEquals(2 + 5, result.size());
    AssertUtils.assertTimeIncreasing(result);
  }

  @Test
  public void testStandstillRuleWithInexactPositions() {
    Period period = Period.ofSeconds(3);
    StepCalculator stepCalculator = new StandstillStepCalculator(BCN_POSITION, TEN_SECONDS, period);

    List<Position> result = stepCalculator.generatePositions();

    assertEquals(2 + 3, result.size());
    AssertUtils.assertTimeIncreasing(result);
  }

  @Test
  public void shouldReturnFirstPosition() {
    Period period = Period.ofSeconds(200);
    StepCalculator stepCalculator = new StandstillStepCalculator(BCN_POSITION, TEN_SECONDS, period);

    Position firstPosition = stepCalculator.getFirstPosition();

    AssertUtils.assertEqualsPosition(BCN_POSITION, firstPosition);
  }

  @Test
  public void shouldReturnLastPosition() {
    Position p = BCN_POSITION;
    Period period = Period.ofSeconds(200);
    StepCalculator stepCalculator = new StandstillStepCalculator(p, TEN_SECONDS, period);

    Position lastPosition = stepCalculator.getLastPosition();

    Position expected = p.afterTime(TEN_SECONDS);
    AssertUtils.assertEqualsPosition(expected, lastPosition);
  }

}
