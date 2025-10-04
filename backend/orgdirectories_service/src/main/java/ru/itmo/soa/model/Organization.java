package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * Organization
 */

public class Organization {

  private Integer id;

  private String name;

  private Coordinates coordinates;

  private LocalDate creationDate;

  private Long annualTurnover;

  private String fullName;

  private Integer employeesCount;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    PUBLIC("PUBLIC"),
    
    TRUST("TRUST"),
    
    OPEN_JOINT_STOCK_COMPANY("OPEN_JOINT_STOCK_COMPANY");

    private final String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  private Address postalAddress;

  public Organization() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Organization(String name, Coordinates coordinates, Long annualTurnover, TypeEnum type) {
    this.name = name;
    this.coordinates = coordinates;
    this.annualTurnover = annualTurnover;
    this.type = type;
  }

  public Organization id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Генерируется автоматически, должно быть больше 0
   * minimum: 1
   * @return id
   */
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Organization name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Поле не может быть null или пустым
   * @return name
   */
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Organization coordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
    return this;
  }

  /**
   * Get coordinates
   * @return coordinates
   */
  @JsonProperty("coordinates")
  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public Organization creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Генерируется автоматически
   * @return creationDate
   */
  @JsonProperty("creationDate")
  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public Organization annualTurnover(Long annualTurnover) {
    this.annualTurnover = annualTurnover;
    return this;
  }

  /**
   * Значение должно быть больше 0
   * minimum: 1
   * @return annualTurnover
   */
  @JsonProperty("annualTurnover")
  public Long getAnnualTurnover() {
    return annualTurnover;
  }

  public void setAnnualTurnover(Long annualTurnover) {
    this.annualTurnover = annualTurnover;
  }

  public Organization fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   * @return fullName
   */
  @JsonProperty("fullName")
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Organization employeesCount(Integer employeesCount) {
    this.employeesCount = employeesCount;
    return this;
  }

  /**
   * Значение должно быть больше 0 если указано
   * minimum: 1
   * @return employeesCount
   */
  @JsonProperty("employeesCount")
  public Integer getEmployeesCount() {
    return employeesCount;
  }

  public void setEmployeesCount(Integer employeesCount) {
    this.employeesCount = employeesCount;
  }

  public Organization type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Organization postalAddress(Address postalAddress) {
    this.postalAddress = postalAddress;
    return this;
  }

  /**
   * Get postalAddress
   * @return postalAddress
   */
  @JsonProperty("postalAddress")
  public Address getPostalAddress() {
    return postalAddress;
  }

  public void setPostalAddress(Address postalAddress) {
    this.postalAddress = postalAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Organization organization = (Organization) o;
    return Objects.equals(this.id, organization.id) &&
        Objects.equals(this.name, organization.name) &&
        Objects.equals(this.coordinates, organization.coordinates) &&
        Objects.equals(this.creationDate, organization.creationDate) &&
        Objects.equals(this.annualTurnover, organization.annualTurnover) &&
        Objects.equals(this.type, organization.type) &&
        Objects.equals(this.postalAddress, organization.postalAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Organization {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    coordinates: ").append(toIndentedString(coordinates)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    annualTurnover: ").append(toIndentedString(annualTurnover)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    employeesCount: ").append(toIndentedString(employeesCount)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    postalAddress: ").append(toIndentedString(postalAddress)).append("\n");
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

