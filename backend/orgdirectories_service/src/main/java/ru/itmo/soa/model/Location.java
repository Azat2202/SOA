package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

/**
 * Location
 */

public class Location {

  private Float x;

  private Double y;

  private Long z;

  private String name;

  public Location() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Location(Float x, Double y, Long z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Location x(Float x) {
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

  public Location y(Double y) {
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

  public Location z(Long z) {
    this.z = z;
    return this;
  }

  /**
   * Поле не может быть null
   * @return z
   */
  @JsonProperty("z")
  public Long getZ() {
    return z;
  }

  public void setZ(Long z) {
    this.z = z;
  }

  public Location name(String name) {
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
    Location location = (Location) o;
    return Objects.equals(this.x, location.x) &&
        Objects.equals(this.y, location.y) &&
        Objects.equals(this.z, location.z) &&
        Objects.equals(this.name, location.name);
  }


  @Override
  public int hashCode() {
    return Objects.hash(x, y, z, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Location {\n");
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

  public ru.itmo.soa.gen.Location toGenLocation() {
    ru.itmo.soa.gen.Location location = new ru.itmo.soa.gen.Location();
    location.setX(x);
    location.setY(y);
    location.setZ(z);
    location.setName(name);
    return location;
  }
}

