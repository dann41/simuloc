package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.trip.Position;

import java.util.concurrent.CompletableFuture;

public interface PositionPlayer {

  void play(Position position);
}
