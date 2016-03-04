package com.dglapps.simuloc.executors;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import com.dglapps.simuloc.listeners.ConsoleRulesExecutorListener;
import com.dglapps.simuloc.listeners.RulesExecutorListener;
import com.dglapps.simuloc.rules.Rule;
import com.dglapps.simuloc.utils.ListUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class RealTimeRulesExecutorTest {

    private Clock clock;
    private RealTimeRulesExecutor realTimeRulesExecutor;

    @Before
    public void setup() {
        this.clock = Clock.systemUTC();
        this.realTimeRulesExecutor = new RealTimeRulesExecutor(clock) {
            @Override
            protected void delay(long delay) {

            }
        };
    }

    @Test
    public void testExecutorOneRuleWithNoPositions() {

        RulesExecutorListener listener = Mockito.mock(RulesExecutorListener.class);
        this.realTimeRulesExecutor.addListener(listener);

        Rule rule = Mockito.mock(Rule.class);
        when(rule.iterator()).thenReturn(new ArrayList<DynamicPosition>().iterator());

        this.realTimeRulesExecutor.execute(ListUtils.arrayToList(rule));

        verify(listener, times(1)).onExecutorStart(eq(realTimeRulesExecutor));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(rule));
        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(rule));
        verify(listener, times(1)).onExecutorEnd(eq(realTimeRulesExecutor));
        verify(listener, times(0)).onPositionGenerated(eq(realTimeRulesExecutor), any(DynamicPosition.class));

    }

    @Test
    public void testExecutorOneRuleWithTwoPositions() {

        RulesExecutorListener listener = spy(new ConsoleRulesExecutorListener(clock));
        this.realTimeRulesExecutor.addListener(listener);

        Rule rule = Mockito.mock(Rule.class);
        when(rule.iterator()).thenReturn(ListUtils.arrayToList(
                PositionFactory.createDynamicPosition(0.0, 0.0, clock.instant().plusSeconds(1).toEpochMilli()),
                PositionFactory.createDynamicPosition(0.0, 0.0, clock.instant().plusSeconds(1).toEpochMilli())
        ).iterator());

        this.realTimeRulesExecutor.execute(ListUtils.arrayToList(rule));

        verify(listener, times(1)).onExecutorStart(eq(realTimeRulesExecutor));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(rule));
        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(rule));
        verify(listener, times(1)).onExecutorEnd(eq(realTimeRulesExecutor));
        verify(listener, times(2)).onPositionGenerated(eq(realTimeRulesExecutor), any(DynamicPosition.class));

    }

    @Test
    public void testExecutorTwoRulesWithTwoPositions() {

        RulesExecutorListener listener = Mockito.mock(RulesExecutorListener.class);
        this.realTimeRulesExecutor.addListener(listener);

        Rule rule1 = Mockito.mock(Rule.class);
        when(rule1.iterator()).thenReturn(ListUtils.arrayToList(
                PositionFactory.createDynamicPosition(0.0, 0.0, 0),
                PositionFactory.createDynamicPosition(0.0, 0.0, 0)
        ).iterator());

        Rule rule2 = Mockito.mock(Rule.class);
        when(rule2.iterator()).thenReturn(ListUtils.arrayToList(
                PositionFactory.createDynamicPosition(0.0, 0.0, 0),
                PositionFactory.createDynamicPosition(0.0, 0.0, 0)
        ).iterator());

        this.realTimeRulesExecutor.execute(ListUtils.arrayToList(rule1, rule2));

        verify(listener, times(1)).onExecutorStart(eq(realTimeRulesExecutor));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(rule1));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(rule2));
        verify(listener, times(2)).onRuleStart(eq(realTimeRulesExecutor), any(Rule.class));

        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(rule1));
        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(rule2));
        verify(listener, times(2)).onRuleEnd(eq(realTimeRulesExecutor), any(Rule.class));

        verify(listener, times(1)).onExecutorEnd(eq(realTimeRulesExecutor));

        verify(listener, times(4)).onPositionGenerated(eq(realTimeRulesExecutor), any(DynamicPosition.class));

    }

}
