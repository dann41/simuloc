package com.dglapps.simuloc.shared.validation;

public class PositiveNumber {

  public static void validate(int property, String propertyName) {
    if (property <= 0) {
      throw new IllegalArgumentException(propertyName + " must be positive");
    }
  }

  public static void validate(double property, String propertyName) {
    if (property <= 0) {
      throw new IllegalArgumentException(propertyName + " must be positive");
    }
  }

}
