package com.dglapps.simuloc;

import com.dglapps.simuloc.domain.trip.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by dani on 26/2/16.
 */
public class AssertUtils {

  public static void assertEqualsPosition(Position expected, Position result) {
    assertEquals(expected.latitude(), result.latitude(), 0.000002);
    assertEquals(expected.longitude(), result.longitude(), 0.000002);
    assertEquals(expected.time(), result.time());
  }

  public static void assertTimeIncreasing(List<Position> positions) {
    Position previous = null;
    for (Position p : positions) {
      if (previous == null) {
        previous = p;
      } else {
        assertTrue(previous.time().isBefore(p.time()));
      }
    }
  }
}
