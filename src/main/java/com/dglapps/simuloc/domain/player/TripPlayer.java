package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.trip.Trip;

import java.time.OffsetDateTime;

/**
 * Created by dani on 25/2/16.
 */
public interface TripPlayer {

    void play(Trip trip, OffsetDateTime startingDateTime);

}
