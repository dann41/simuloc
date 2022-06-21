package com.dglapps.simuloc.utils;

public class NotNull {

    public static void validate(Object property, String message) {
        if (property == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
