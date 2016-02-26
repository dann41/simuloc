package com.dglapps.simuloc.entities;

public class AddressLocation {

    Address address;
    Position position;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return (address != null ? address.toString() : "null")
                + " (" + (position != null ? position.toString() : "null") + ")";
    }
}
