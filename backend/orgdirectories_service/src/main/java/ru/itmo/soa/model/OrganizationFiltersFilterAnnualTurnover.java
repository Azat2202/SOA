package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * OrganizationFiltersFilterAnnualTurnover
 */

@JsonTypeName("OrganizationFilters_filter_annualTurnover")
public class OrganizationFiltersFilterAnnualTurnover {

  private Integer min;

  private Integer max;

  public OrganizationFiltersFilterAnnualTurnover min(Integer min) {
    this.min = min;
    return this;
  }

  /**
   * Get min
   * @return min
   */
  
  @JsonProperty("min")
  public Integer getMin() {
    return min;
  }

  public void setMin(Integer min) {
    this.min = min;
  }

  public OrganizationFiltersFilterAnnualTurnover max(Integer max) {
    this.max = max;
    return this;
  }

  /**
   * Get max
   * @return max
   */
  
  @JsonProperty("max")
  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
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
    OrganizationFiltersFilterAnnualTurnover organizationFiltersFilterAnnualTurnover = (OrganizationFiltersFilterAnnualTurnover) o;
    return Objects.equals(this.min, organizationFiltersFilterAnnualTurnover.min) &&
        Objects.equals(this.max, organizationFiltersFilterAnnualTurnover.max);
  }

  @Override
  public int hashCode() {
    return Objects.hash(min, max);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationFiltersFilterAnnualTurnover {\n");
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

