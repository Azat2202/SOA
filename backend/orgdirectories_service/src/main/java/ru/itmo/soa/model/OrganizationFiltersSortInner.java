package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * OrganizationFiltersSortInner
 */

@JsonTypeName("OrganizationFilters_sort_inner")
public class OrganizationFiltersSortInner {

  /**
   * Gets or Sets field
   */
  public enum FieldEnum {
    ID("ID"),
    
    NAME("NAME"),
    
    CREATION_DATE("CREATION_DATE"),
    
    ANNUAL_TURNOVER("ANNUAL_TURNOVER"),
    
    FULL_NAME("FULL_NAME"),
    
    EMPLOYEES_COUNT("EMPLOYEES_COUNT"),
    
    TYPE("TYPE"),
    
    ADDRESS_STREET("ADDRESS_STREET"),
    
    ADDRESS_TOWN_X("ADDRESS_TOWN_X"),
    
    ADDRESS_TOWN_Y("ADDRESS_TOWN_Y"),
    
    ADDRESS_TOWN_Z("ADDRESS_TOWN_Z"),
    
    ADDRESS_TOWN_NAME("ADDRESS_TOWN_NAME"),
    
    COORDINATES_X("COORDINATES_X"),
    
    COORDINATES_Y("COORDINATES_Y"),
    
    LOCATION_NAME("LOCATION_NAME");

    private final String value;

    FieldEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FieldEnum fromValue(String value) {
      for (FieldEnum b : FieldEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private FieldEnum field = FieldEnum.ID;

  /**
   * Gets or Sets direction
   */
  public enum DirectionEnum {
    ASC("ASC"),
    
    DESC("DESC");

    private final String value;

    DirectionEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DirectionEnum fromValue(String value) {
      for (DirectionEnum b : DirectionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private DirectionEnum direction = DirectionEnum.ASC;

  public OrganizationFiltersSortInner field(FieldEnum field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
   */
  
  @JsonProperty("field")
  public FieldEnum getField() {
    return field;
  }

  public void setField(FieldEnum field) {
    this.field = field;
  }

  public OrganizationFiltersSortInner direction(DirectionEnum direction) {
    this.direction = direction;
    return this;
  }

  /**
   * Get direction
   * @return direction
   */
  
  @JsonProperty("direction")
  public DirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(DirectionEnum direction) {
    this.direction = direction;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationFiltersSortInner organizationFiltersSortInner = (OrganizationFiltersSortInner) o;
    return Objects.equals(this.field, organizationFiltersSortInner.field) &&
        Objects.equals(this.direction, organizationFiltersSortInner.direction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, direction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationFiltersSortInner {\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
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

