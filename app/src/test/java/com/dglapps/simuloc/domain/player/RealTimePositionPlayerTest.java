package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.trip.Position;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

import static com.dglapps.simuloc.utils.CoordinatesMother.BCN;
import static com.dglapps.simuloc.utils.DurationMother.TEN_SECONDS;

public class RealTimePositionPlayerTest {

  private Clock clock;
  private RealTimePositionPlayer positionPlayer;

  @BeforeEach
  public void setup() {
    this.clock = Clock.systemUTC();
    this.positionPlayer = new RealTimePositionPlayer(clock);
  }

  @Test
  public void givenPositionInFutureWhenPlayingPositionThenWaitUntilTimeReached() {
    OffsetDateTime now = OffsetDateTime.now(clock);
    Position positionIn10Seconds = Position.aBuilder(BCN)
        .withTime(now.plus(TEN_SECONDS))
        .build();

    CompletableFuture<Void> task = this.positionPlayer.play(positionIn10Seconds);

    Awaitility.await().atMost(TEN_SECONDS).until(task::isDone);

  }

}
