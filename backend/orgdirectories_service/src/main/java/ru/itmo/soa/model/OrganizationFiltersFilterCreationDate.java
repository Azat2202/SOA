package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDate;
import java.util.Objects;

/**
 * OrganizationFiltersFilterCreationDate
 */

@JsonTypeName("OrganizationFilters_filter_creationDate")
public class OrganizationFiltersFilterCreationDate {

  private LocalDate min;

  private LocalDate max;

  public OrganizationFiltersFilterCreationDate min(LocalDate min) {
    this.min = min;
    return this;
  }

  /**
   * Get min
   * @return min
   */
  @JsonProperty("min")
  public LocalDate getMin() {
    return min;
  }

  public void setMin(LocalDate min) {
    this.min = min;
  }

  public OrganizationFiltersFilterCreationDate max(LocalDate max) {
    this.max = max;
    return this;
  }

  /**
   * Get max
   * @return max
   */
  @JsonProperty("max")
  public LocalDate getMax() {
    return max;
  }

  public void setMax(LocalDate max) {
    this.max = max;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationFiltersFilterCreationDate organizationFiltersFilterCreationDate = (OrganizationFiltersFilterCreationDate) o;
    return Objects.equals(this.min, organizationFiltersFilterCreationDate.min) &&
        Objects.equals(this.max, organizationFiltersFilterCreationDate.max);
  }

  @Override
  public int hashCode() {
    return Objects.hash(min, max);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationFiltersFilterCreationDate {\n");
    sb.append("    min: ").append(toIndentedString(min)).append("\n");
    sb.append("    max: ").append(toIndentedString(max)).append("\n");
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

