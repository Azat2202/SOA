package ru.itmo.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets OrganizationStatus
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.15.0")
public enum OrganizationStatus {
  
  INITIATED("INITIATED"),
  
  MONEY_TAKEN("MONEY_TAKEN"),
  
  ORGANIZATION_CREATED("ORGANIZATION_CREATED"),
  
  MONEY_RETURNING("MONEY_RETURNING"),
  
  NOT_ENOUGH_MONEY("NOT_ENOUGH_MONEY");

  private final String value;

  OrganizationStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static OrganizationStatus fromValue(String value) {
    for (OrganizationStatus b : OrganizationStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

