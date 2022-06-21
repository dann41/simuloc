package com.dglapps.simuloc.utils;

import java.util.Collection;

public class NotEmpty {

    public static <T> void validate(Collection<T> property, String propertyName) {
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException(propertyName + " cannot be empty");
        }
    }

}
