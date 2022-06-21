package com.dglapps.simuloc.domain;

public class Frequency {
    private final int value;

    private Frequency(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Frequency ofPositionsPerMinute(int value) {
        return new Frequency(value);
    }
}
