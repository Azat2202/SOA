package ru.itmo.soa.service;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.itmo.soa.external.OrganizationsClient;
import ru.itmo.soa.model.*;


@RequestScoped
public class OrgDirectoryService {
    @Inject
    OrganizationsClient organizationsClient;

    public OrganizationArray getOrganizationsInRange(Integer min, Integer max, Pagination pagination) {
        OrganizationFilters filters = new OrganizationFilters();
        OrganizationFiltersFilter filter = new OrganizationFiltersFilter();
        OrganizationFiltersFilterAnnualTurnover turnoverFilter = new OrganizationFiltersFilterAnnualTurnover();

        turnoverFilter.setMin(min);
        turnoverFilter.setMax(max);
        filter = filter.annualTurnover(turnoverFilter);
        filters = filters.filter(filter);
        filters = filters.pagination(pagination);

        return organizationsClient.getOrganizations(filters);
    }

    public OrganizationArray getOrganizationsByType(OrganizationFiltersFilter.TypeEnum type, Pagination pagination) {
        OrganizationFilters filters = new OrganizationFilters();
        OrganizationFiltersFilter filter = new OrganizationFiltersFilter();

        filter = filter.type(type);
        filters = filters.filter(filter);
        filters = filters.pagination(pagination);
        return organizationsClient.getOrganizations(filters);
    }
}
