package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by dani on 3/3/16.
 */
public class CompositeStepTestCalculator {

    @Test
    public void testCompositeRuleWithNoRules() {
        StepCalculator stepCalculator = new CompositeStepCalculator();

        List<DynamicPosition> list = stepCalculator.generatePositions();
        assertNotNull(list);
        assertTrue(list.isEmpty());

        assertNull(stepCalculator.getFirstPosition());
        assertNull(stepCalculator.getLastPosition());

        Iterator<DynamicPosition> iterator = stepCalculator.iterator();
        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testCompositeRuleWithSingleRule() {
        StepCalculator stepCalculator1 = new StandstillStepCalculator(PositionFactory.createDynamicPosition(0.0, 0.0, 0), 5000, 1000);

        StepCalculator stepCalculator = new CompositeStepCalculator(stepCalculator1);

        List<DynamicPosition> list = stepCalculator.generatePositions();
        assertNotNull(list);
        assertEquals(stepCalculator1.generatePositions().size(), list.size());

        assertNotNull(stepCalculator.getFirstPosition());
        AssertUtils.assertEqualsPosition(stepCalculator1.getFirstPosition(), stepCalculator.getFirstPosition());

        assertNotNull(stepCalculator.getLastPosition());
        AssertUtils.assertEqualsPosition(stepCalculator1.getLastPosition(), stepCalculator.getLastPosition());

        Iterator<DynamicPosition> iterator = stepCalculator.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
    }

    @Test
    public void testCompositeRuleWith2Rules() {
        StepCalculator stepCalculator1 = new StandstillStepCalculator(PositionFactory.createDynamicPosition(0.0, 0.0, 0), 5000, 1000);
        StepCalculator stepCalculator2 = new StandstillStepCalculator(PositionFactory.createDynamicPosition(1.0, 1.0, 0), 15000, 2000);

        StepCalculator stepCalculator = new CompositeStepCalculator(stepCalculator1, stepCalculator2);

        List<DynamicPosition> list = stepCalculator.generatePositions();
        assertNotNull(list);

        int totalExpected = stepCalculator1.generatePositions().size() + stepCalculator2.generatePositions().size();
        assertEquals(totalExpected, list.size());

        assertNotNull(stepCalculator.getFirstPosition());
        AssertUtils.assertEqualsPosition(stepCalculator1.getFirstPosition(), stepCalculator.getFirstPosition());

        assertNotNull(stepCalculator.getLastPosition());
        AssertUtils.assertEqualsPosition(stepCalculator2.getLastPosition(), stepCalculator.getLastPosition());

        Iterator<DynamicPosition> iterator = stepCalculator.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
    }

}
