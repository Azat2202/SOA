package ru.itmo.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import ru.itmo.gen.model.Organization;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrganizationArray
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.15.0")
public class OrganizationArray {

  @Valid
  private List<@Valid Organization> organizations = new ArrayList<>();

  private @Nullable Integer totalCount;

  private @Nullable Integer page;

  private @Nullable Integer size;

  public OrganizationArray organizations(List<@Valid Organization> organizations) {
    this.organizations = organizations;
    return this;
  }

  public OrganizationArray addOrganizationsItem(Organization organizationsItem) {
    if (this.organizations == null) {
      this.organizations = new ArrayList<>();
    }
    this.organizations.add(organizationsItem);
    return this;
  }

  /**
   * Get organizations
   * @return organizations
   */
  @Valid 
  @Schema(name = "organizations", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("organizations")
  public List<@Valid Organization> getOrganizations() {
    return organizations;
  }

  public void setOrganizations(List<@Valid Organization> organizations) {
    this.organizations = organizations;
  }

  public OrganizationArray totalCount(@Nullable Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  /**
   * Get totalCount
   * @return totalCount
   */
  
  @Schema(name = "totalCount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalCount")
  public @Nullable Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(@Nullable Integer totalCount) {
    this.totalCount = totalCount;
  }

  public OrganizationArray page(@Nullable Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Get page
   * @return page
   */
  
  @Schema(name = "page", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("page")
  public @Nullable Integer getPage() {
    return page;
  }

  public void setPage(@Nullable Integer page) {
    this.page = page;
  }

  public OrganizationArray size(@Nullable Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * @return size
   */
  
  @Schema(name = "size", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("size")
  public @Nullable Integer getSize() {
    return size;
  }

  public void setSize(@Nullable Integer size) {
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationArray organizationArray = (OrganizationArray) o;
    return Objects.equals(this.organizations, organizationArray.organizations) &&
        Objects.equals(this.totalCount, organizationArray.totalCount) &&
        Objects.equals(this.page, organizationArray.page) &&
        Objects.equals(this.size, organizationArray.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(organizations, totalCount, page, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationArray {\n");
    sb.append("    organizations: ").append(toIndentedString(organizations)).append("\n");
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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

