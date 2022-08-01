package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.NotEmpty;

public record Name(String value) {

  public Name {
    NotEmpty.validate(value, "name");
  }
}
