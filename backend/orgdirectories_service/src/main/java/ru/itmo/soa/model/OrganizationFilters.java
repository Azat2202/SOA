package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * OrganizationFilters
 */

public class OrganizationFilters {

  private Pagination pagination;

  
  private List< OrganizationFiltersSortInner> sort = new ArrayList<>();

  private OrganizationFiltersFilter filter;

  public OrganizationFilters pagination(Pagination pagination) {
    this.pagination = pagination;
    return this;
  }

  /**
   * Get pagination
   * @return pagination
   */
   
  @JsonProperty("pagination")
  public Pagination getPagination() {
    return pagination;
  }

  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }

  public OrganizationFilters sort(List< OrganizationFiltersSortInner> sort) {
    this.sort = sort;
    return this;
  }

  public OrganizationFilters addSortItem(OrganizationFiltersSortInner sortItem) {
    if (this.sort == null) {
      this.sort = new ArrayList<>();
    }
    this.sort.add(sortItem);
    return this;
  }

  /**
   * Get sort
   * @return sort
   */
   
  @JsonProperty("sort")
  public List< OrganizationFiltersSortInner> getSort() {
    return sort;
  }

  public void setSort(List< OrganizationFiltersSortInner> sort) {
    this.sort = sort;
  }

  public OrganizationFilters filter(OrganizationFiltersFilter filter) {
    this.filter = filter;
    return this;
  }

  /**
   * Get filter
   * @return filter
   */
  @JsonProperty("filter")
  public OrganizationFiltersFilter getFilter() {
    return filter;
  }

  public void setFilter(OrganizationFiltersFilter filter) {
    this.filter = filter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationFilters organizationFilters = (OrganizationFilters) o;
    return Objects.equals(this.pagination, organizationFilters.pagination) &&
        Objects.equals(this.sort, organizationFilters.sort) &&
        Objects.equals(this.filter, organizationFilters.filter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pagination, sort, filter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationFilters {\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
    sb.append("    sort: ").append(toIndentedString(sort)).append("\n");
    sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
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

