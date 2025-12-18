package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * OrganizationFiltersFilter
 */

@JsonTypeName("OrganizationFilters_filter")
public class OrganizationFiltersFilter {

  private Long organizationId;

  private String name;

  private OrganizationFiltersFilterCoordinates coordinates;

  private OrganizationFiltersFilterCreationDate creationDate;

  private OrganizationFiltersFilterAnnualTurnover annualTurnover;

  private String fullName;

  private Long employeesCount;

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

    public static TypeEnum fromType(ru.itmo.soa.gen.OrganizationType organizationType) {
      return TypeEnum.valueOf(organizationType.name());
    }
  }

  private TypeEnum type;

  private OrganizationFiltersFilterPostalAddress postalAddress;

  public OrganizationFiltersFilter organizationId(Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Get organizationId
   * minimum: 1
   * @return organizationId
   */
  @JsonProperty("organizationId")
  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public OrganizationFiltersFilter name(String name) {
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

  public OrganizationFiltersFilter coordinates(OrganizationFiltersFilterCoordinates coordinates) {
    this.coordinates = coordinates;
    return this;
  }

  /**
   * Get coordinates
   * @return coordinates
   */
  @JsonProperty("coordinates")
  public OrganizationFiltersFilterCoordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(OrganizationFiltersFilterCoordinates coordinates) {
    this.coordinates = coordinates;
  }

  public OrganizationFiltersFilter creationDate(OrganizationFiltersFilterCreationDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Get creationDate
   * @return creationDate
   */
  @JsonProperty("creationDate")
  public OrganizationFiltersFilterCreationDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OrganizationFiltersFilterCreationDate creationDate) {
    this.creationDate = creationDate;
  }

  public OrganizationFiltersFilter annualTurnover(OrganizationFiltersFilterAnnualTurnover annualTurnover) {
    this.annualTurnover = annualTurnover;
    return this;
  }

  /**
   * Get annualTurnover
   * @return annualTurnover
   */
  @JsonProperty("annualTurnover")
  public OrganizationFiltersFilterAnnualTurnover getAnnualTurnover() {
    return annualTurnover;
  }

  public void setAnnualTurnover(OrganizationFiltersFilterAnnualTurnover annualTurnover) {
    this.annualTurnover = annualTurnover;
  }

  public OrganizationFiltersFilter fullName(String fullName) {
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

  public OrganizationFiltersFilter employeesCount(Long employeesCount) {
    this.employeesCount = employeesCount;
    return this;
  }

  /**
   * Get employeesCount
   * minimum: 1
   * @return employeesCount
   */
  @JsonProperty("employeesCount")
  public Long getEmployeesCount() {
    return employeesCount;
  }

  public void setEmployeesCount(Long employeesCount) {
    this.employeesCount = employeesCount;
  }

  public OrganizationFiltersFilter type(TypeEnum type) {
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

  public OrganizationFiltersFilter postalAddress(OrganizationFiltersFilterPostalAddress postalAddress) {
    this.postalAddress = postalAddress;
    return this;
  }

  /**
   * Get postalAddress
   * @return postalAddress
   */
  @JsonProperty("postalAddress")
  public OrganizationFiltersFilterPostalAddress getPostalAddress() {
    return postalAddress;
  }

  public void setPostalAddress(OrganizationFiltersFilterPostalAddress postalAddress) {
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
    OrganizationFiltersFilter organizationFiltersFilter = (OrganizationFiltersFilter) o;
    return Objects.equals(this.organizationId, organizationFiltersFilter.organizationId) &&
        Objects.equals(this.coordinates, organizationFiltersFilter.coordinates) &&
        Objects.equals(this.creationDate, organizationFiltersFilter.creationDate) &&
        Objects.equals(this.annualTurnover, organizationFiltersFilter.annualTurnover) &&
        Objects.equals(this.fullName, organizationFiltersFilter.fullName) &&
        Objects.equals(this.employeesCount, organizationFiltersFilter.employeesCount) &&
        Objects.equals(this.type, organizationFiltersFilter.type) &&
        Objects.equals(this.postalAddress, organizationFiltersFilter.postalAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizationId, name, coordinates, creationDate, annualTurnover, fullName, employeesCount, type, postalAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationFiltersFilter {\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
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

