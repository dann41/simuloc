package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.trip.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static com.dglapps.simuloc.utils.CoordinatesMother.BCN;
import static com.dglapps.simuloc.utils.DurationMother.TEN_SECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RealTimePositionPlayerTest {

  private Clock clock;
  private RealTimePositionPlayer positionPlayer;

  @BeforeEach
  public void setup() {
    this.clock = Clock.systemUTC();
    this.positionPlayer = new RealTimePositionPlayer(clock);
  }

  @Test
  public void givenPositionWhenPlayingPositionThenWaitUntilTimeReached() {
    OffsetDateTime now = OffsetDateTime.now(clock);
    Position positionIn10Seconds = Position.aBuilder(BCN)
        .withTime(now.plus(TEN_SECONDS))
        .build();

    assertDurationAtMost(() -> this.positionPlayer.play(positionIn10Seconds), TEN_SECONDS);
  }

  void assertDurationAtMost(Runnable runnable, Duration atMostDuration) {
    var start = LocalDateTime.now();
    runnable.run();
    var end = LocalDateTime.now();

    assertThat(Duration.between(start, end)).isLessThan(atMostDuration);
  }

}
