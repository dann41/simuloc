package com.dglapps.simuloc.entities;

public class AddressLocation {

    private final Address address;
    private final Position position;

    public AddressLocation(Address address, Position position) {
        this.address = address;
        this.position = position;
    }

    public Address getAddress() {
        return address;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return (address != null ? address.toString() : "null")
                + " (" + (position != null ? position.toString() : "null") + ")";
    }
}
