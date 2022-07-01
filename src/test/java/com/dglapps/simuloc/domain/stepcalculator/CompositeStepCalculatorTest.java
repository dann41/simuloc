package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.Position;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static com.dglapps.simuloc.utils.PositionMother.BCN_POSITION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CompositeStepCalculatorTest {

    @Test
    public void testCompositeRuleWithNoRules() {
        StepCalculator stepCalculator = new CompositeStepCalculator();

        List<Position> list = stepCalculator.generatePositions();
        assertNotNull(list);
        assertTrue(list.isEmpty());

        assertNull(stepCalculator.getFirstPosition());
        assertNull(stepCalculator.getLastPosition());

        assertThat(stepCalculator.generatePositions()).isEmpty();
    }

    @Test
    public void testCompositeRuleWithSingleRule() {
        StepCalculator stepCalculator1 = new StandstillStepCalculator(
                BCN_POSITION,
                Duration.ofSeconds(5),
                Period.ofSeconds(1)
        );

        StepCalculator stepCalculator = new CompositeStepCalculator(stepCalculator1);

        List<Position> list = stepCalculator.generatePositions();
        assertNotNull(list);
        assertEquals(stepCalculator1.generatePositions().size(), list.size());

        assertNotNull(stepCalculator.getFirstPosition());
        AssertUtils.assertEqualsPosition(stepCalculator1.getFirstPosition(), stepCalculator.getFirstPosition());

        assertNotNull(stepCalculator.getLastPosition());
        AssertUtils.assertEqualsPosition(stepCalculator1.getLastPosition(), stepCalculator.getLastPosition());
    }

    @Test
    public void testCompositeRuleWith2Rules() {
        StepCalculator stepCalculator1 = new StandstillStepCalculator(
                BCN_POSITION,
                Duration.ofSeconds(5),
                Period.ofSeconds(1)
        );
        StepCalculator stepCalculator2 = new StandstillStepCalculator(
                BCN_POSITION,
                Duration.ofSeconds(15),
                Period.ofSeconds(2)
        );

        StepCalculator stepCalculator = new CompositeStepCalculator(stepCalculator1, stepCalculator2);

        List<Position> list = stepCalculator.generatePositions();
        assertNotNull(list);

        int totalExpected = stepCalculator1.generatePositions().size() + stepCalculator2.generatePositions().size();
        assertEquals(totalExpected, list.size());

        assertNotNull(stepCalculator.getFirstPosition());
        AssertUtils.assertEqualsPosition(stepCalculator1.getFirstPosition(), stepCalculator.getFirstPosition());

        assertNotNull(stepCalculator.getLastPosition());
        AssertUtils.assertEqualsPosition(stepCalculator2.getLastPosition(), stepCalculator.getLastPosition());
    }

}
