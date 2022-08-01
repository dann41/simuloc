package com.dglapps.simuloc.application.player;

import com.dglapps.simuloc.domain.player.TripPlayer;
import com.dglapps.simuloc.domain.player.listener.ReactiveTripPlayerListener;
import com.dglapps.simuloc.domain.trip.Position;
import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.domain.trip.TripFinder;
import com.dglapps.simuloc.domain.trip.TripId;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

public class ReactiveTripPlayer {

  private final TripFinder tripFinder;
  private final TripPlayer tripPlayer;

  public ReactiveTripPlayer(TripFinder tripFinder, TripPlayer tripPlayer) {
    this.tripFinder = tripFinder;
    this.tripPlayer = tripPlayer;
  }

  public Flux<Position> execute(TripId tripId) {
    Trip trip = tripFinder.execute(tripId);

    ReactiveTripPlayerListener reactiveTripPlayerListener = new ReactiveTripPlayerListener();
    tripPlayer.addListener(reactiveTripPlayerListener);

    var flux =  reactiveTripPlayerListener.positions();
    CompletableFuture.runAsync(() ->  tripPlayer.play(trip, OffsetDateTime.now()));
    return flux;
  }
}
