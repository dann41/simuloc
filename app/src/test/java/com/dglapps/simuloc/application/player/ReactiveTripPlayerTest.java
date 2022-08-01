package com.dglapps.simuloc.application.player;

import com.dglapps.simuloc.domain.player.RealTimePositionPlayer;
import com.dglapps.simuloc.domain.player.TripObjectMother;
import com.dglapps.simuloc.domain.player.TripPlayer;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculatorFactory;
import com.dglapps.simuloc.domain.trip.Position;
import com.dglapps.simuloc.domain.trip.TripFinder;
import com.dglapps.simuloc.domain.trip.TripId;
import com.dglapps.simuloc.domain.trip.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReactiveTripPlayerTest {

  private ReactiveTripPlayer reactiveTripPlayer;

  @Mock
  private TripRepository tripRepository;

  @BeforeEach
  public void setup() {
    reactiveTripPlayer = new ReactiveTripPlayer(
        new TripFinder(tripRepository),
        new TripPlayer(
            new StepCalculatorFactory(),
            new RealTimePositionPlayer(Clock.systemUTC())
        )
    );
  }

  @Test
  public void shouldEmitPositions() {
    TripId tripId = new TripId("1234");
    given(tripRepository.findBy(any())).willReturn(Optional.of(TripObjectMother.aTripWithTwoSteps()));

    Flux<Position> flux = reactiveTripPlayer.execute(tripId);

    StepVerifier.create(flux)
        .expectNextCount(23)
        .verifyComplete();
  }

}
