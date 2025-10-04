package ru.itmo.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * OrganizationArray
 */

public class OrganizationArray {


    private List<Organization> organizations = new ArrayList<>();

    private Integer totalCount;

    private Integer page;

    private Integer size;

    public OrganizationArray organizations(List<Organization> organizations) {
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
     *
     * @return organizations
     */

    @JsonProperty("organizations")
    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public OrganizationArray totalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    /**
     * Get totalCount
     *
     * @return totalCount
     */

    @JsonProperty("totalCount")
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public OrganizationArray page(Integer page) {
        this.page = page;
        return this;
    }

    /**
     * Get page
     *
     * @return page
     */

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public OrganizationArray size(Integer size) {
        this.size = size;
        return this;
    }

    /**
     * Get size
     *
     * @return size
     */

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
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

