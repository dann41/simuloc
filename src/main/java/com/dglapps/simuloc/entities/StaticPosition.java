package com.dglapps.simuloc.entities;

/**
 * Created by dani on 26/2/16.
 */
public class StaticPosition implements Position {

    private double latitude;
    private double longitude;
    private double altitude;

    public StaticPosition(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    public StaticPosition(Position position) {
        this(position.getLatitude(), position.getLongitude());
        this.altitude = position.getAltitude();
    }

    /**
     * @return latitude in decimal unit (e.g. 23.1345213)
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude in decimal unit (e.g. 23.1345213)
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return longitude in decimal unit (e.g. 23.1345213)
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude in decimal unit (e.g. 23.1345213)
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return altitude in meters
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * @param altitude in meters
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String toString() {
        return latitude + "," + longitude;
    }

}
