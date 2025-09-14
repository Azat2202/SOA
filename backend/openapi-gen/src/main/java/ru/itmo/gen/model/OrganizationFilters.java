package ru.itmo.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import ru.itmo.gen.model.OrganizationFiltersFilter;
import ru.itmo.gen.model.OrganizationFiltersSortInner;
import ru.itmo.gen.model.Pagination;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrganizationFilters
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-15T00:43:06.965813+03:00[Europe/Moscow]", comments = "Generator version: 7.15.0")
public class OrganizationFilters {

  private @Nullable Pagination pagination;

  @Valid
  private List<@Valid OrganizationFiltersSortInner> sort = new ArrayList<>();

  private @Nullable OrganizationFiltersFilter filter;

  public OrganizationFilters pagination(@Nullable Pagination pagination) {
    this.pagination = pagination;
    return this;
  }

  /**
   * Get pagination
   * @return pagination
   */
  @Valid 
  @Schema(name = "pagination", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pagination")
  public @Nullable Pagination getPagination() {
    return pagination;
  }

  public void setPagination(@Nullable Pagination pagination) {
    this.pagination = pagination;
  }

  public OrganizationFilters sort(List<@Valid OrganizationFiltersSortInner> sort) {
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
  @Valid 
  @Schema(name = "sort", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sort")
  public List<@Valid OrganizationFiltersSortInner> getSort() {
    return sort;
  }

  public void setSort(List<@Valid OrganizationFiltersSortInner> sort) {
    this.sort = sort;
  }

  public OrganizationFilters filter(@Nullable OrganizationFiltersFilter filter) {
    this.filter = filter;
    return this;
  }

  /**
   * Get filter
   * @return filter
   */
  @Valid 
  @Schema(name = "filter", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("filter")
  public @Nullable OrganizationFiltersFilter getFilter() {
    return filter;
  }

  public void setFilter(@Nullable OrganizationFiltersFilter filter) {
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

