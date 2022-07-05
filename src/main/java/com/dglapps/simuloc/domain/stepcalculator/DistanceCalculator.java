package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.domain.trip.Coordinates;

public class DistanceCalculator {

  public static long getDistance(Coordinates p1, Coordinates p2) {
    return (long) (1000 * distance(p1.latitude(), p1.longitude(), p2.latitude(), p2.longitude()));
  }

  private static double distance(double lat1, double lon1, double lat2, double lon2) {
    double theta = lon1 - lon2;
    double dist = Math.sin(decimalToRadians(lat1))
        * Math.sin(decimalToRadians(lat2))
        + Math.cos(decimalToRadians(lat1))
        * Math.cos(decimalToRadians(lat2))
        * Math.cos(decimalToRadians(theta)
    );

    dist = Math.acos(dist);
    dist = radiansToDecimals(dist);
    dist = dist * 60 * 1.1515;
    dist = dist * 1.609344;

    return (dist);
  }

  private static double decimalToRadians(double deg) {
    return (deg * Math.PI / 180.0);
  }

  private static double radiansToDecimals(double rad) {
    return (rad * 180 / Math.PI);
  }
}
