package com.dglapps.simuloc.shared.validation;

import java.util.Collection;

public class NotEmpty {

    public static <T> void validate(Collection<T> property, String propertyName) {
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException(propertyName + " cannot be empty");
        }
    }

    public static void validate(String property, String propertyName) {
        if (property == null || property.isEmpty() || property.isBlank()) {
            throw new IllegalArgumentException(propertyName + " cannot be empty");
        }
    }

}
