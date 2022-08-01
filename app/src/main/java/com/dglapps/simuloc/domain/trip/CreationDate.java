package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.NotNull;

import java.time.OffsetDateTime;

public record CreationDate(OffsetDateTime value) {

  public CreationDate {
    NotNull.validate(value, "creationDate");
  }

  public static CreationDate now() {
    return new CreationDate(OffsetDateTime.now());
  }

}
