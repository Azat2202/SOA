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
 * OrganizationFiltersFilterCoordinates
 */

@JsonTypeName("OrganizationFilters_filter_coordinates")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-15T00:43:06.965813+03:00[Europe/Moscow]", comments = "Generator version: 7.15.0")
public class OrganizationFiltersFilterCoordinates {

  private @Nullable Integer x;

  private @Nullable Double y;

  public OrganizationFiltersFilterCoordinates x(@Nullable Integer x) {
    this.x = x;
    return this;
  }

  /**
   * Get x
   * minimum: -365
   * @return x
   */
  @Min(-365) 
  @Schema(name = "x", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("x")
  public @Nullable Integer getX() {
    return x;
  }

  public void setX(@Nullable Integer x) {
    this.x = x;
  }

  public OrganizationFiltersFilterCoordinates y(@Nullable Double y) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationFiltersFilterCoordinates organizationFiltersFilterCoordinates = (OrganizationFiltersFilterCoordinates) o;
    return Objects.equals(this.x, organizationFiltersFilterCoordinates.x) &&
        Objects.equals(this.y, organizationFiltersFilterCoordinates.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationFiltersFilterCoordinates {\n");
    sb.append("    x: ").append(toIndentedString(x)).append("\n");
    sb.append("    y: ").append(toIndentedString(y)).append("\n");
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

