package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * OrganizationFiltersFilterPostalAddress
 */

@JsonTypeName("OrganizationFilters_filter_postalAddress")
public class OrganizationFiltersFilterPostalAddress {

  private String street;

  private OrganizationFiltersFilterPostalAddressTown town;

  public OrganizationFiltersFilterPostalAddress street(String street) {
    this.street = street;
    return this;
  }

  /**
   * Get street
   * @return street
   */
  
  @JsonProperty("street")
  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public OrganizationFiltersFilterPostalAddress town(OrganizationFiltersFilterPostalAddressTown town) {
    this.town = town;
    return this;
  }

  /**
   * Get town
   * @return town
   */
  @JsonProperty("town")
  public OrganizationFiltersFilterPostalAddressTown getTown() {
    return town;
  }

  public void setTown(OrganizationFiltersFilterPostalAddressTown town) {
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
    OrganizationFiltersFilterPostalAddress organizationFiltersFilterPostalAddress = (OrganizationFiltersFilterPostalAddress) o;
    return Objects.equals(this.street, organizationFiltersFilterPostalAddress.street) &&
        Objects.equals(this.town, organizationFiltersFilterPostalAddress.town);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, town);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationFiltersFilterPostalAddress {\n");
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

