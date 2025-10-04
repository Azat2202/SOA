package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Address
 */

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

