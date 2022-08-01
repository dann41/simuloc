package com.dglapps.simuloc.domain.player.listener;

import com.dglapps.simuloc.domain.player.TripPlayer;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;
import com.dglapps.simuloc.domain.trip.Position;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class ReactiveTripPlayerListener implements TripPlayerListener {
  private final Flux<Position> flux;
  private FluxSink<Position> emitter;

  public ReactiveTripPlayerListener() {
    this.flux = Flux.create(emitter -> this.emitter = emitter);
  }

  @Override
  public void onTripPlayerStart(TripPlayer tripPlayer) {
    System.out.println("TRIP START");
  }

  @Override
  public void onRuleStart(TripPlayer tripPlayer, StepCalculator stepCalculator) {
    System.out.println("RULE START");
  }

  @Override
  public void onRuleEnd(TripPlayer tripPlayer, StepCalculator stepCalculator) {
    System.out.println("RULE END");
  }

  @Override
  public void onPositionGenerated(TripPlayer tripPlayer, Position position) {
    if (emitter == null || emitter.isCancelled()) {
      return;
    }
    System.out.println("NEXT");
    emitter.next(position);
  }

  @Override
  public void onTripPlayerEnd(TripPlayer tripPlayer) {
    System.out.println("TRIP END");
    emitter.complete();
    tripPlayer.removeListener(this);
  }

  public Flux<Position> positions() {
    return flux;
  }
}
