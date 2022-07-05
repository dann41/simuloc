package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;

public interface TripPlayerObservable {

  void addListener(TripPlayerListener listener);

  void removeListener(TripPlayerListener listener);
}
