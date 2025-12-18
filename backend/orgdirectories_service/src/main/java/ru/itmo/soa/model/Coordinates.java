package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Coordinates
 */

public class Coordinates {

  private Integer x;

  private Double y;

  public Coordinates() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Coordinates(Integer x, Double y) {
    this.x = x;
    this.y = y;
  }

  public Coordinates x(Integer x) {
    this.x = x;
    return this;
  }

  /**
   * Значение должно быть больше -366
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

  public Coordinates y(Double y) {
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
    Coordinates coordinates = (Coordinates) o;
    return Objects.equals(this.x, coordinates.x) &&
        Objects.equals(this.y, coordinates.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Coordinates {\n");
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

  public ru.itmo.soa.gen.Coordinates toGenCoordinates() {
    ru.itmo.soa.gen.Coordinates result = new ru.itmo.soa.gen.Coordinates();
    result.setX(x);
    result.setY(y);
    return result;
  }
}

