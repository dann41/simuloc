package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.infrastructure.player.listener.ConsoleTripPlayerListener;
import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;
import com.dglapps.simuloc.domain.trip.Coordinates;
import com.dglapps.simuloc.domain.trip.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static com.dglapps.simuloc.utils.PositionMother.BCN_POSITION;
import static org.mockito.Mockito.*;

public class RealTimeTripPlayerTest {

    private Clock clock;
    private RealTimeTripPlayer realTimeRulesExecutor;

    @BeforeEach
    public void setup() {
        this.clock = Clock.systemUTC();
        this.realTimeRulesExecutor = new RealTimeTripPlayer(clock) {
            @Override
            protected void delay(long delay) {

            }
        };
    }

    @Test
    public void testExecutorOneRuleWithNoPositions() {

        TripPlayerListener listener = Mockito.mock(TripPlayerListener.class);
        this.realTimeRulesExecutor.addListener(listener);

        StepCalculator stepCalculator = Mockito.mock(StepCalculator.class);

        this.realTimeRulesExecutor.execute(List.of(stepCalculator));

        verify(listener, times(1)).onExecutorStart(eq(realTimeRulesExecutor));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(stepCalculator));
        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(stepCalculator));
        verify(listener, times(1)).onExecutorEnd(eq(realTimeRulesExecutor));
        verify(listener, times(0)).onPositionGenerated(eq(realTimeRulesExecutor), any(Position.class));

    }

    @Test
    public void testExecutorOneRuleWithTwoPositions() {

        TripPlayerListener listener = spy(new ConsoleTripPlayerListener(clock));
        this.realTimeRulesExecutor.addListener(listener);

        StepCalculator stepCalculator = Mockito.mock(StepCalculator.class);
        when(stepCalculator.generatePositions()).thenReturn(Arrays.asList(
                Position.aBuilder(new Coordinates(0.0, 0.0))
                        .withTime(clock.instant().plusSeconds(1).atOffset(ZoneOffset.UTC))
                        .build(),
                Position.aBuilder(new Coordinates(0.0, 0.0))
                        .withTime(clock.instant().plusSeconds(1).atOffset(ZoneOffset.UTC))
                        .build()
        ));

        this.realTimeRulesExecutor.execute(List.of(stepCalculator));

        verify(listener, times(1)).onExecutorStart(eq(realTimeRulesExecutor));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(stepCalculator));
        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(stepCalculator));
        verify(listener, times(1)).onExecutorEnd(eq(realTimeRulesExecutor));
        verify(listener, times(2)).onPositionGenerated(eq(realTimeRulesExecutor), any(Position.class));

    }

    @Test
    public void testExecutorTwoRulesWithTwoPositions() {
        TripPlayerListener listener = Mockito.mock(TripPlayerListener.class);
        this.realTimeRulesExecutor.addListener(listener);

        StepCalculator stepCalculator1 = Mockito.mock(StepCalculator.class);
        when(stepCalculator1.generatePositions()).thenReturn(Arrays.asList(
                BCN_POSITION,
                BCN_POSITION
        ));

        StepCalculator stepCalculator2 = Mockito.mock(StepCalculator.class);
        when(stepCalculator2.generatePositions()).thenReturn(Arrays.asList(
                BCN_POSITION,
                BCN_POSITION
        ));

        this.realTimeRulesExecutor.execute(Arrays.asList(stepCalculator1, stepCalculator2));

        verify(listener, times(1)).onExecutorStart(eq(realTimeRulesExecutor));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(stepCalculator1));
        verify(listener, times(1)).onRuleStart(eq(realTimeRulesExecutor), eq(stepCalculator2));

        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(stepCalculator1));
        verify(listener, times(1)).onRuleEnd(eq(realTimeRulesExecutor), eq(stepCalculator2));

        verify(listener, times(1)).onExecutorEnd(eq(realTimeRulesExecutor));

        verify(listener, times(4)).onPositionGenerated(eq(realTimeRulesExecutor), any(Position.class));

    }

}
