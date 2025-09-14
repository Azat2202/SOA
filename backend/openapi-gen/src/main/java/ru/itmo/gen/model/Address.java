package ru.itmo.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import ru.itmo.gen.model.Location;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Address
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-15T00:43:06.965813+03:00[Europe/Moscow]", comments = "Generator version: 7.15.0")
public class Address {

  private String street;

  private Location town;

  public Address() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Address(String street, Location town) {
    this.street = street;
    this.town = town;
  }

  public Address street(String street) {
    this.street = street;
    return this;
  }

  /**
   * Строка не может быть пустой
   * @return street
   */
  @NotNull @Size(min = 1) 
  @Schema(name = "street", description = "Строка не может быть пустой", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("street")
  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Address town(Location town) {
    this.town = town;
    return this;
  }

  /**
   * Get town
   * @return town
   */
  @NotNull @Valid 
  @Schema(name = "town", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("town")
  public Location getTown() {
    return town;
  }

  public void setTown(Location town) {
    this.town = town;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(this.street, address.street) &&
        Objects.equals(this.town, address.town);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, town);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Address {\n");
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    town: ").append(toIndentedString(town)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

