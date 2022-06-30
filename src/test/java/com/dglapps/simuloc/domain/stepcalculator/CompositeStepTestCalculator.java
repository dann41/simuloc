package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.domain.trip.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by dani on 3/3/16.
 */
public class CompositeStepTestCalculator {

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
        /*StepCalculator stepCalculator1 = new StandstillStepCalculator(
                Position.aBuilder(new Coordinates(0, 0)).build(),
                5000,
                1000
        );

        StepCalculator stepCalculator = new CompositeStepCalculator(stepCalculator1);

        List<Position> list = stepCalculator.generatePositions();
        assertNotNull(list);
        assertEquals(stepCalculator1.generatePositions().size(), list.size());

        assertNotNull(stepCalculator.getFirstPosition());
        AssertUtils.assertEqualsPosition(stepCalculator1.getFirstPosition(), stepCalculator.getFirstPosition());

        assertNotNull(stepCalculator.getLastPosition());
        AssertUtils.assertEqualsPosition(stepCalculator1.getLastPosition(), stepCalculator.getLastPosition());

        Iterator<Position> iterator = stepCalculator.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());*/
    }

    @Test
    public void testCompositeRuleWith2Rules() {
        /*StepCalculator stepCalculator1 = new StandstillStepCalculator(
                Position.aBuilder(new Coordinates(0, 0)).build(),
                5000,
                1000
        );
        StepCalculator stepCalculator2 = new StandstillStepCalculator(
                Position.aBuilder(new Coordinates(1.0, 1.0)).build(),
                15000,
                2000
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

        Iterator<Position> iterator = stepCalculator.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());*/
    }

}
