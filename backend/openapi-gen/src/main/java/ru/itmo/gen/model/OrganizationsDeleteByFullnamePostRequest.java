package ru.itmo.gen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrganizationsDeleteByFullnamePostRequest
 */

@JsonTypeName("_organizations_delete_by_fullname_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.15.0")
public class OrganizationsDeleteByFullnamePostRequest {

  private @Nullable String fullname;

  public OrganizationsDeleteByFullnamePostRequest fullname(@Nullable String fullname) {
    this.fullname = fullname;
    return this;
  }

  /**
   * Get fullname
   * @return fullname
   */
  @Size(min = 1, max = 918) 
  @Schema(name = "fullname", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fullname")
  public @Nullable String getFullname() {
    return fullname;
  }

  public void setFullname(@Nullable String fullname) {
    this.fullname = fullname;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationsDeleteByFullnamePostRequest organizationsDeleteByFullnamePostRequest = (OrganizationsDeleteByFullnamePostRequest) o;
    return Objects.equals(this.fullname, organizationsDeleteByFullnamePostRequest.fullname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fullname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationsDeleteByFullnamePostRequest {\n");
    sb.append("    fullname: ").append(toIndentedString(fullname)).append("\n");
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

