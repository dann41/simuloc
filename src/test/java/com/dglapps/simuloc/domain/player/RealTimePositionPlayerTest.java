package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.infrastructure.player.listener.ConsoleTripPlayerListener;
import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;
import com.dglapps.simuloc.domain.trip.Coordinates;
import com.dglapps.simuloc.domain.trip.Position;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static com.dglapps.simuloc.utils.CoordinatesMother.BCN;
import static com.dglapps.simuloc.utils.DurationMother.ONE_SECOND;
import static com.dglapps.simuloc.utils.DurationMother.TEN_SECONDS;
import static com.dglapps.simuloc.utils.PositionMother.BCN_POSITION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RealTimePositionPlayerTest {

    private Clock clock;
    private RealTimePositionPlayer positionPlayer;

    @BeforeEach
    public void setup() {
        this.clock = Clock.systemUTC();
        this.positionPlayer = new RealTimePositionPlayer(clock);
    }

    @Test
    public void givenPositionInFuture_whenPlayingPosition_thenWaitUntilTimeReached() {
        OffsetDateTime now = OffsetDateTime.now(clock);
        Position positionIn10Seconds = Position.aBuilder(BCN)
                .withTime(now.plus(TEN_SECONDS))
                .build();

        CompletableFuture<Void> task = this.positionPlayer.play(positionIn10Seconds);

        Awaitility.await().atMost(TEN_SECONDS).until(task::isDone);

    }

}
