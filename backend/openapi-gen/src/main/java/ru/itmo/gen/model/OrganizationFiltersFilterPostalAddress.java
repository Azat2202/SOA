package ru.itmo.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import ru.itmo.gen.model.OrganizationFiltersFilterPostalAddressTown;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrganizationFiltersFilterPostalAddress
 */

@JsonTypeName("OrganizationFilters_filter_postalAddress")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.15.0")
public class OrganizationFiltersFilterPostalAddress {

  private @Nullable String street;

  private @Nullable OrganizationFiltersFilterPostalAddressTown town;

  public OrganizationFiltersFilterPostalAddress street(@Nullable String street) {
    this.street = street;
    return this;
  }

  /**
   * Get street
   * @return street
   */
  
  @Schema(name = "street", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("street")
  public @Nullable String getStreet() {
    return street;
  }

  public void setStreet(@Nullable String street) {
    this.street = street;
  }

  public OrganizationFiltersFilterPostalAddress town(@Nullable OrganizationFiltersFilterPostalAddressTown town) {
    this.town = town;
    return this;
  }

  /**
   * Get town
   * @return town
   */
  @Valid 
  @Schema(name = "town", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("town")
  public @Nullable OrganizationFiltersFilterPostalAddressTown getTown() {
    return town;
  }

  public void setTown(@Nullable OrganizationFiltersFilterPostalAddressTown town) {
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

