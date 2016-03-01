package com.dglapps.simuloc.entities;

public interface Position {

    /**
     * @return latitude in decimal unit (e.g. 23.1345213)
     */
    double getLatitude();

    /**
     * @param latitude in decimal unit (e.g. 23.1345213)
     */
    void setLatitude(double latitude);

    /**
     * @return longitude in decimal unit (e.g. 23.1345213)
     */
    double getLongitude();

    /**
     * @param longitude in decimal unit (e.g. 23.1345213)
     */
    void setLongitude(double longitude);

    /**
     * @return altitude in meters
     */
    double getAltitude();

    /**
     * @param altitude in meters
     */
    void setAltitude(double altitude);

}
