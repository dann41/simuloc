package com.dglapps.simuloc.domain.player.listener;

import com.dglapps.simuloc.domain.player.TripPlayer;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;
import com.dglapps.simuloc.domain.trip.Position;

public interface TripPlayerListener {

  void onTripPlayerStart(TripPlayer tripPlayer);

  void onRuleStart(TripPlayer tripPlayer, StepCalculator stepCalculator);

  void onRuleEnd(TripPlayer tripPlayer, StepCalculator stepCalculator);

  void onPositionGenerated(TripPlayer tripPlayer, Position position);

  void onTripPlayerEnd(TripPlayer tripPlayer);
}
