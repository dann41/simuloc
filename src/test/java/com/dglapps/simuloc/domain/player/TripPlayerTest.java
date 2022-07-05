package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.stepcalculator.StandstillStepCalculator;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculatorFactory;
import com.dglapps.simuloc.domain.stepcalculator.StraightStepCalculator;
import com.dglapps.simuloc.domain.trip.Position;
import com.dglapps.simuloc.domain.trip.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TripPlayerTest {

  private Clock clock;
  private TripPlayer tripPlayer;

  @BeforeEach
  public void setup() {
    this.clock = Clock.systemUTC();
    this.tripPlayer = new TripPlayer(
        new StepCalculatorFactory(),
        position -> CompletableFuture.completedFuture(null)
    );
  }

  @Test
  public void givenTripWithoutStepsWhenPlayTripThenDoNotEmitPosition() {

    TripPlayerListener listener = Mockito.mock(TripPlayerListener.class);
    this.tripPlayer.addListener(listener);

    Trip trip = TripObjectMother.aTripWithoutSteps();

    this.tripPlayer.play(trip, OffsetDateTime.now());

    verify(listener, times(1)).onExecutorStart(eq(tripPlayer));
    verify(listener, times(0)).onRuleStart(eq(tripPlayer), any(StandstillStepCalculator.class));
    verify(listener, times(0)).onRuleEnd(eq(tripPlayer), any(StandstillStepCalculator.class));
    verify(listener, times(1)).onExecutorEnd(eq(tripPlayer));
    verify(listener, times(0)).onPositionGenerated(eq(tripPlayer), any(Position.class));

  }

  @Test
  public void givenTripWithTwoStepsWhenPlayTripThenDoCallCallbacks() {
    TripPlayerListener listener = Mockito.mock(TripPlayerListener.class);
    this.tripPlayer.addListener(listener);
    Trip trip = TripObjectMother.aTripWithTwoSteps();

    this.tripPlayer.play(trip, OffsetDateTime.now());

    verify(listener, times(1)).onExecutorStart(eq(tripPlayer));
    verify(listener, times(1)).onRuleStart(eq(tripPlayer), any(StandstillStepCalculator.class));
    verify(listener, times(1)).onRuleStart(eq(tripPlayer), any(StraightStepCalculator.class));

    verify(listener, times(1)).onRuleEnd(eq(tripPlayer), any(StandstillStepCalculator.class));
    verify(listener, times(1)).onRuleEnd(eq(tripPlayer), any(StraightStepCalculator.class));

    verify(listener, times(1)).onExecutorEnd(eq(tripPlayer));

    verify(listener, times(24)).onPositionGenerated(eq(tripPlayer), any(Position.class));

  }

}
