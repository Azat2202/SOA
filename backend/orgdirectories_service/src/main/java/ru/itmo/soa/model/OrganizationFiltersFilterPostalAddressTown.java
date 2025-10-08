package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * OrganizationFiltersFilterPostalAddressTown
 */

@JsonTypeName("OrganizationFilters_filter_postalAddress_town")
public class OrganizationFiltersFilterPostalAddressTown {

  private Float x;

  private Double y;

  private Long z;

  private String name;

  public OrganizationFiltersFilterPostalAddressTown x(Float x) {
    this.x = x;
    return this;
  }

  /**
   * Get x
   * @return x
   */
  
  @JsonProperty("x")
  public Float getX() {
    return x;
  }

  public void setX(Float x) {
    this.x = x;
  }

  public OrganizationFiltersFilterPostalAddressTown y(Double y) {
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

  public OrganizationFiltersFilterPostalAddressTown z(Long z) {
    this.z = z;
    return this;
  }

  /**
   * Get z
   * @return z
   */
  
  @JsonProperty("z")
  public Long getZ() {
    return z;
  }

  public void setZ(Long z) {
    this.z = z;
  }

  public OrganizationFiltersFilterPostalAddressTown name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
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

