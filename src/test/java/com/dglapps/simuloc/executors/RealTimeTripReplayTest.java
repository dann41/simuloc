package com.dglapps.simuloc.executors;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import com.dglapps.simuloc.listeners.ConsoleRulesExecutorListener;
import com.dglapps.simuloc.listeners.RulesExecutorListener;
import com.dglapps.simuloc.rules.StepCalculator;
import com.dglapps.simuloc.utils.ListUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class RealTimeTripReplayTest {

    private Clock clock;
    private RealTimeTripReplay realTimeRulesExecutor;

    @BeforeEach
    public void setup() {
        this.clock = Clock.systemUTC();
        this.realTimeRulesExecutor = new RealTimeTripReplay(clock) {
            @Override
            protected void delay(long delay) {

            }
        };
    }

    @Test
    public void testExecutorOneRuleWithNoPositions() {

        RulesExecutorListener listener = Mockito.mock(RulesExecutorListener.class);
        this.realTimeRulesExecutor.addListener(listener);

        StepCalculator stepCalculator = Mockito.mock(StepCalculator.class);
        when(stepCalculator.iterator()).thenReturn(new ArrayList<DynamicPosition>().iterator());

        this.realTimeRulesExecutor.execute(ListUtils.arrayToList(stepCalculator));

        verify(listener, times(1)).onExecutorStart(eq(realTimeRulesExecutor));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(stepCalculator));
        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(stepCalculator));
        verify(listener, times(1)).onExecutorEnd(eq(realTimeRulesExecutor));
        verify(listener, times(0)).onPositionGenerated(eq(realTimeRulesExecutor), any(DynamicPosition.class));

    }

    @Test
    public void testExecutorOneRuleWithTwoPositions() {

        RulesExecutorListener listener = spy(new ConsoleRulesExecutorListener(clock));
        this.realTimeRulesExecutor.addListener(listener);

        StepCalculator stepCalculator = Mockito.mock(StepCalculator.class);
        when(stepCalculator.iterator()).thenReturn(ListUtils.arrayToList(
                PositionFactory.createDynamicPosition(0.0, 0.0, clock.instant().plusSeconds(1).toEpochMilli()),
                PositionFactory.createDynamicPosition(0.0, 0.0, clock.instant().plusSeconds(1).toEpochMilli())
        ).iterator());

        this.realTimeRulesExecutor.execute(ListUtils.arrayToList(stepCalculator));

        verify(listener, times(1)).onExecutorStart(eq(realTimeRulesExecutor));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(stepCalculator));
        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(stepCalculator));
        verify(listener, times(1)).onExecutorEnd(eq(realTimeRulesExecutor));
        verify(listener, times(2)).onPositionGenerated(eq(realTimeRulesExecutor), any(DynamicPosition.class));

    }

    @Test
    public void testExecutorTwoRulesWithTwoPositions() {

        RulesExecutorListener listener = Mockito.mock(RulesExecutorListener.class);
        this.realTimeRulesExecutor.addListener(listener);

        StepCalculator stepCalculator1 = Mockito.mock(StepCalculator.class);
        when(stepCalculator1.iterator()).thenReturn(ListUtils.arrayToList(
                PositionFactory.createDynamicPosition(0.0, 0.0, 0),
                PositionFactory.createDynamicPosition(0.0, 0.0, 0)
        ).iterator());

        StepCalculator stepCalculator2 = Mockito.mock(StepCalculator.class);
        when(stepCalculator2.iterator()).thenReturn(ListUtils.arrayToList(
                PositionFactory.createDynamicPosition(0.0, 0.0, 0),
                PositionFactory.createDynamicPosition(0.0, 0.0, 0)
        ).iterator());

        this.realTimeRulesExecutor.execute(ListUtils.arrayToList(stepCalculator1, stepCalculator2));

        verify(listener, times(1)).onExecutorStart(eq(realTimeRulesExecutor));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(stepCalculator1));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(stepCalculator2));
        verify(listener, times(2)).onRuleStart(eq(realTimeRulesExecutor), any(StepCalculator.class));

        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(stepCalculator1));
        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(stepCalculator2));
        verify(listener, times(2)).onRuleEnd(eq(realTimeRulesExecutor), any(StepCalculator.class));

        verify(listener, times(1)).onExecutorEnd(eq(realTimeRulesExecutor));

        verify(listener, times(4)).onPositionGenerated(eq(realTimeRulesExecutor), any(DynamicPosition.class));

    }

}
