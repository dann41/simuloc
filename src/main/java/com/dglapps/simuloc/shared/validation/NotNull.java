package com.dglapps.simuloc.shared.validation;

public class NotNull {

  public static void validate(Object property, String message) {
    if (property == null) {
      throw new IllegalArgumentException(message);
    }
  }

}
