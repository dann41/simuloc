package com.dglapps.simuloc.entities;

public class Address {

    private final String value;

    public Address(String value) {
        this.value = value;
    }

    public String getAddress() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
