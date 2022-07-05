package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.trip.Position;

import java.time.Clock;
import java.util.concurrent.CompletableFuture;

public class RealTimePositionPlayer implements PositionPlayer {

  private final Clock clock;

  public RealTimePositionPlayer(Clock clock) {
    this.clock = clock;
  }

  @Override
  public CompletableFuture<Void> play(Position position) {
    return CompletableFuture.runAsync(() -> {
      if (position != null) {
        long timeToSleep = position.time().toEpochSecond() * 1000 - clock.millis();
        if (timeToSleep > 0) {
          delay(timeToSleep);
        }
      }
    });
  }

  private void delay(long delay) {
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
