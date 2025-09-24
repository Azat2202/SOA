package ru.itmo.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrganizationFiltersFilterPostalAddressTown
 */

@JsonTypeName("OrganizationFilters_filter_postalAddress_town")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.15.0")
public class OrganizationFiltersFilterPostalAddressTown {

  private @Nullable Float x;

  private @Nullable Double y;

  private @Nullable Long z;

  private @Nullable String name;

  public OrganizationFiltersFilterPostalAddressTown x(@Nullable Float x) {
    this.x = x;
    return this;
  }

  /**
   * Get x
   * @return x
   */
  
  @Schema(name = "x", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("x")
  public @Nullable Float getX() {
    return x;
  }

  public void setX(@Nullable Float x) {
    this.x = x;
  }

  public OrganizationFiltersFilterPostalAddressTown y(@Nullable Double y) {
    this.y = y;
    return this;
  }

  /**
   * Get y
   * @return y
   */
  
  @Schema(name = "y", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("y")
  public @Nullable Double getY() {
    return y;
  }

  public void setY(@Nullable Double y) {
    this.y = y;
  }

  public OrganizationFiltersFilterPostalAddressTown z(@Nullable Long z) {
    this.z = z;
    return this;
  }

  /**
   * Get z
   * @return z
   */
  
  @Schema(name = "z", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("z")
  public @Nullable Long getZ() {
    return z;
  }

  public void setZ(@Nullable Long z) {
    this.z = z;
  }

  public OrganizationFiltersFilterPostalAddressTown name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationFiltersFilterPostalAddressTown organizationFiltersFilterPostalAddressTown = (OrganizationFiltersFilterPostalAddressTown) o;
    return Objects.equals(this.x, organizationFiltersFilterPostalAddressTown.x) &&
        Objects.equals(this.y, organizationFiltersFilterPostalAddressTown.y) &&
        Objects.equals(this.z, organizationFiltersFilterPostalAddressTown.z) &&
        Objects.equals(this.name, organizationFiltersFilterPostalAddressTown.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationFiltersFilterPostalAddressTown {\n");
    sb.append("    x: ").append(toIndentedString(x)).append("\n");
    sb.append("    y: ").append(toIndentedString(y)).append("\n");
    sb.append("    z: ").append(toIndentedString(z)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

