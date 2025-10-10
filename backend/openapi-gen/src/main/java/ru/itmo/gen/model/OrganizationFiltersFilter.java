package ru.itmo.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import ru.itmo.gen.model.OrganizationFiltersFilterAnnualTurnover;
import ru.itmo.gen.model.OrganizationFiltersFilterCoordinates;
import ru.itmo.gen.model.OrganizationFiltersFilterCreationDate;
import ru.itmo.gen.model.OrganizationFiltersFilterPostalAddress;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrganizationFiltersFilter
 */

@JsonTypeName("OrganizationFilters_filter")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.15.0")
public class OrganizationFiltersFilter {

  private @Nullable Long organizationId;

  private @Nullable String name;

  private @Nullable OrganizationFiltersFilterCoordinates coordinates;

  private @Nullable OrganizationFiltersFilterCreationDate creationDate;

  private @Nullable OrganizationFiltersFilterAnnualTurnover annualTurnover;

  private @Nullable String fullName;

  private @Nullable Long employeesCount;

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

  private @Nullable TypeEnum type;

  private @Nullable OrganizationFiltersFilterPostalAddress postalAddress;

  public OrganizationFiltersFilter organizationId(@Nullable Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  /**
   * Get organizationId
   * minimum: 1
   * @return organizationId
   */
  @Min(1L) 
  @Schema(name = "organizationId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("organizationId")
  public @Nullable Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(@Nullable Long organizationId) {
    this.organizationId = organizationId;
  }

  public OrganizationFiltersFilter name(@Nullable String name) {
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

  public OrganizationFiltersFilter coordinates(@Nullable OrganizationFiltersFilterCoordinates coordinates) {
    this.coordinates = coordinates;
    return this;
  }

  /**
   * Get coordinates
   * @return coordinates
   */
  @Valid 
  @Schema(name = "coordinates", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coordinates")
  public @Nullable OrganizationFiltersFilterCoordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(@Nullable OrganizationFiltersFilterCoordinates coordinates) {
    this.coordinates = coordinates;
  }

  public OrganizationFiltersFilter creationDate(@Nullable OrganizationFiltersFilterCreationDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Get creationDate
   * @return creationDate
   */
  @Valid 
  @Schema(name = "creationDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("creationDate")
  public @Nullable OrganizationFiltersFilterCreationDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(@Nullable OrganizationFiltersFilterCreationDate creationDate) {
    this.creationDate = creationDate;
  }

  public OrganizationFiltersFilter annualTurnover(@Nullable OrganizationFiltersFilterAnnualTurnover annualTurnover) {
    this.annualTurnover = annualTurnover;
    return this;
  }

  /**
   * Get annualTurnover
   * @return annualTurnover
   */
  @Valid 
  @Schema(name = "annualTurnover", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("annualTurnover")
  public @Nullable OrganizationFiltersFilterAnnualTurnover getAnnualTurnover() {
    return annualTurnover;
  }

  public void setAnnualTurnover(@Nullable OrganizationFiltersFilterAnnualTurnover annualTurnover) {
    this.annualTurnover = annualTurnover;
  }

  public OrganizationFiltersFilter fullName(@Nullable String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   * @return fullName
   */
  @Size(max = 918) 
  @Schema(name = "fullName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fullName")
  public @Nullable String getFullName() {
    return fullName;
  }

  public void setFullName(@Nullable String fullName) {
    this.fullName = fullName;
  }

  public OrganizationFiltersFilter employeesCount(@Nullable Long employeesCount) {
    this.employeesCount = employeesCount;
    return this;
  }

  /**
   * Get employeesCount
   * minimum: 1
   * @return employeesCount
   */
  @Min(1L) 
  @Schema(name = "employeesCount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("employeesCount")
  public @Nullable Long getEmployeesCount() {
    return employeesCount;
  }

  public void setEmployeesCount(@Nullable Long employeesCount) {
    this.employeesCount = employeesCount;
  }

  public OrganizationFiltersFilter type(@Nullable TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  
  @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public @Nullable TypeEnum getType() {
    return type;
  }

  public void setType(@Nullable TypeEnum type) {
    this.type = type;
  }

  public OrganizationFiltersFilter postalAddress(@Nullable OrganizationFiltersFilterPostalAddress postalAddress) {
    this.postalAddress = postalAddress;
    return this;
  }

  /**
   * Get postalAddress
   * @return postalAddress
   */
  @Valid 
  @Schema(name = "postalAddress", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("postalAddress")
  public @Nullable OrganizationFiltersFilterPostalAddress getPostalAddress() {
    return postalAddress;
  }

  public void setPostalAddress(@Nullable OrganizationFiltersFilterPostalAddress postalAddress) {
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
        Objects.equals(this.name, organizationFiltersFilter.name) &&
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

