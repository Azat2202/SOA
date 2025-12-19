package ru.itmo.soa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFilters;
import ru.itmo.gen.model.OrganizationFiltersFilter;
import ru.itmo.gen.model.OrganizationFiltersFilterAnnualTurnover;
import ru.itmo.gen.model.Pagination;
import ru.itmo.soa.external.OrganizationsClient;

@Service
@RequiredArgsConstructor
public class OrgDirectoryService {

    private final OrganizationsClient organizationsClient;

    public OrganizationArray getOrganizationsInRange(Integer min, Integer max, Pagination pagination) {
        OrganizationFiltersFilterAnnualTurnover turnoverFilter = new OrganizationFiltersFilterAnnualTurnover()
                .min(min)
                .max(max);

        OrganizationFiltersFilter filter = new OrganizationFiltersFilter()
                .annualTurnover(turnoverFilter);

        OrganizationFilters filters = new OrganizationFilters()
                .filter(filter)
                .pagination(pagination);

        return organizationsClient.getOrganizations(filters);
    }

    public OrganizationArray getOrganizationsByType(OrganizationFiltersFilter.TypeEnum type, Pagination pagination) {
        OrganizationFiltersFilter filter = new OrganizationFiltersFilter()
                .type(type);

        OrganizationFilters filters = new OrganizationFilters()
                .filter(filter)
                .pagination(pagination);

        return organizationsClient.getOrganizations(filters);
    }
}
