package ru.itmo.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import ru.itmo.gen.model.Address;
import ru.itmo.gen.model.Coordinates;
import java.util.NoSuchElementException;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Organization
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-15T00:43:06.965813+03:00[Europe/Moscow]", comments = "Generator version: 7.15.0")
public class Organization {

  private @Nullable Integer id;

  private String name;

  private Coordinates coordinates;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private @Nullable LocalDate creationDate;

  private Long annualTurnover;

  private JsonNullable<@Size(max = 918) String> fullName = JsonNullable.<String>undefined();

  private JsonNullable<@Min(1) Integer> employeesCount = JsonNullable.<Integer>undefined();

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

  private @Nullable Address postalAddress;

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

  public Organization id(@Nullable Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Генерируется автоматически, должно быть больше 0
   * minimum: 1
   * @return id
   */
  @Min(1) 
  @Schema(name = "id", accessMode = Schema.AccessMode.READ_ONLY, description = "Генерируется автоматически, должно быть больше 0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public @Nullable Integer getId() {
    return id;
  }

  public void setId(@Nullable Integer id) {
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
  @NotNull @Size(min = 1) 
  @Schema(name = "name", description = "Поле не может быть null или пустым", requiredMode = Schema.RequiredMode.REQUIRED)
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
  @NotNull @Valid 
  @Schema(name = "coordinates", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("coordinates")
  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public Organization creationDate(@Nullable LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Генерируется автоматически
   * @return creationDate
   */
  @Valid 
  @Schema(name = "creationDate", accessMode = Schema.AccessMode.READ_ONLY, description = "Генерируется автоматически", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("creationDate")
  public @Nullable LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(@Nullable LocalDate creationDate) {
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
  @NotNull @Min(1L) 
  @Schema(name = "annualTurnover", description = "Значение должно быть больше 0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("annualTurnover")
  public Long getAnnualTurnover() {
    return annualTurnover;
  }

  public void setAnnualTurnover(Long annualTurnover) {
    this.annualTurnover = annualTurnover;
  }

  public Organization fullName(String fullName) {
    this.fullName = JsonNullable.of(fullName);
    return this;
  }

  /**
   * Get fullName
   * @return fullName
   */
  @Size(max = 918) 
  @Schema(name = "fullName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fullName")
  public JsonNullable<@Size(max = 918) String> getFullName() {
    return fullName;
  }

  public void setFullName(JsonNullable<String> fullName) {
    this.fullName = fullName;
  }

  public Organization employeesCount(Integer employeesCount) {
    this.employeesCount = JsonNullable.of(employeesCount);
    return this;
  }

  /**
   * Значение должно быть больше 0 если указано
   * minimum: 1
   * @return employeesCount
   */
  @Min(1) 
  @Schema(name = "employeesCount", description = "Значение должно быть больше 0 если указано", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("employeesCount")
  public JsonNullable<@Min(1) Integer> getEmployeesCount() {
    return employeesCount;
  }

  public void setEmployeesCount(JsonNullable<Integer> employeesCount) {
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
  @NotNull 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Organization postalAddress(@Nullable Address postalAddress) {
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
  public @Nullable Address getPostalAddress() {
    return postalAddress;
  }

  public void setPostalAddress(@Nullable Address postalAddress) {
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
        equalsNullable(this.fullName, organization.fullName) &&
        equalsNullable(this.employeesCount, organization.employeesCount) &&
        Objects.equals(this.type, organization.type) &&
        Objects.equals(this.postalAddress, organization.postalAddress);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, coordinates, creationDate, annualTurnover, hashCodeNullable(fullName), hashCodeNullable(employeesCount), type, postalAddress);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
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

