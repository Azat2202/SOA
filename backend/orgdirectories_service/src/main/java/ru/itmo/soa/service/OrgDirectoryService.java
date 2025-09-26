package ru.itmo.soa.service;


import org.springframework.http.ResponseEntity;
import ru.itmo.gen.model.*;
import ru.itmo.soa.external.OrganizationsClient;

import javax.inject.Inject;

public class OrgDirectoryService {
    @Inject
    OrganizationsClient organizationsClient;

    public ResponseEntity<OrganizationArray> getOrganizationsInRange(Integer min, Integer max, Pagination pagination) {
        OrganizationFilters filters = new OrganizationFilters();
        OrganizationFiltersFilter filter = new OrganizationFiltersFilter();
        OrganizationFiltersFilterAnnualTurnover turnoverFilter = new OrganizationFiltersFilterAnnualTurnover();

        turnoverFilter.setMin(min);
        turnoverFilter.setMax(max);
        filter = filter.annualTurnover(turnoverFilter);
        filters = filters.filter(filter);
        filters = filters.pagination(pagination);

        OrganizationArray organizations = organizationsClient.getOrganizations(filters);

        return ResponseEntity.ok(organizations);
    }

    public ResponseEntity<OrganizationArray> getOrganizationsByType(OrganizationFiltersFilter.TypeEnum type, Pagination pagination) {
        OrganizationFilters filters = new OrganizationFilters();
        OrganizationFiltersFilter filter = new OrganizationFiltersFilter();

        filter = filter.type(type);
        filters = filters.filter(filter);
        filters = filters.pagination(pagination);

        OrganizationArray organizations = organizationsClient.getOrganizations(filters);

        return ResponseEntity.ok(organizations);
    }
}
