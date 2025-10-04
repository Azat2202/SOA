package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * OrganizationFiltersFilterCoordinates
 */

@JsonTypeName("OrganizationFilters_filter_coordinates")
public class OrganizationFiltersFilterCoordinates {

  private Integer x;

  private Double y;

  public OrganizationFiltersFilterCoordinates x(Integer x) {
    this.x = x;
    return this;
  }

  /**
   * Get x
   * minimum: -365
   * @return x
   */
  @JsonProperty("x")
  public Integer getX() {
    return x;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public OrganizationFiltersFilterCoordinates y(Double y) {
    this.y = y;
    return this;
  }

  /**
   * Get y
   * @return y
   */
  
  @JsonProperty("y")
  public Double getY() {
    return y;
  }

  public void setY(Double y) {
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

