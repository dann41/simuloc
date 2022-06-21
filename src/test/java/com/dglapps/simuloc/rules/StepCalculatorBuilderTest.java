package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by dani on 26/2/16.
 */
public class StepCalculatorBuilderTest {

    private TripBuilder builder;

    @Test
    public void testRulesBuilderAddStandstill() {
        DynamicPosition firstPosition = PositionFactory.createDynamicPosition(0, 0, 0);
        long duration = 5000;
        long throughput = 1000;
        DynamicPosition lastPosition = PositionFactory.createDynamicPosition(0, 0, firstPosition.getTime() + duration);

        builder = new TripBuilder(firstPosition);

        List<StepCalculator> stepCalculators;

        stepCalculators = builder.build();
        assertTrue(stepCalculators.isEmpty());

        builder.addStandstillStep(duration, throughput);

        stepCalculators = builder.build();
        assertEquals(1, stepCalculators.size());
        assertEquals(StandstillStepCalculator.class, stepCalculators.get(0).getClass());
        AssertUtils.assertEqualsPosition(firstPosition, stepCalculators.get(0).getFirstPosition());
        AssertUtils.assertEqualsPosition(lastPosition, stepCalculators.get(0).getLastPosition());
    }

    @Test
    public void testRulesBuilderAddSingleRoute() {
        long initTime = 1000;
        DynamicPosition firstPosition = PositionFactory.createDynamicPosition(0, 0, initTime);
        long throughput = 1000;
        DynamicPosition lastPosition = PositionFactory.createDynamicPosition(0, 0, 213234);

        builder = new TripBuilder(firstPosition);

        List<StepCalculator> stepCalculators;

        stepCalculators = builder.build();
        assertTrue(stepCalculators.isEmpty());

        long duration = 100000;
        builder.addStraightStep(lastPosition, duration, throughput);

        stepCalculators = builder.build();
        assertEquals(1, stepCalculators.size());
        assertEquals(StraightStepCalculator.class, stepCalculators.get(0).getClass());

        lastPosition = PositionFactory.cloneDynamicPosition(lastPosition);
        lastPosition.setTime(initTime + duration);

        AssertUtils.assertEqualsPosition(firstPosition, stepCalculators.get(0).getFirstPosition());
        AssertUtils.assertEqualsPosition(lastPosition, stepCalculators.get(0).getLastPosition());
    }

}
