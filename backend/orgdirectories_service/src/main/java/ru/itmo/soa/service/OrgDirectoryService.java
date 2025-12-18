package ru.itmo.soa.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.itmo.soa.external.OrganizationsClient;
import ru.itmo.soa.model.*;

@Service
@RequiredArgsConstructor
public class OrgDirectoryService {

    private final OrganizationsClient organizationsClient;

    public OrganizationArray getOrganizationsInRange(Integer min, Integer max, Pagination pagination) {
        OrganizationFilters filters = new OrganizationFilters();
        OrganizationFiltersFilter filter = new OrganizationFiltersFilter();
        OrganizationFiltersFilterAnnualTurnover turnoverFilter = new OrganizationFiltersFilterAnnualTurnover();

        turnoverFilter.setMin(min);
        turnoverFilter.setMax(max);
        filter = filter.annualTurnover(turnoverFilter);
        filters = filters.filter(filter);
        filters = filters.pagination(pagination);

        System.out.println(filters);
        OrganizationArray array;
        try{
            array = organizationsClient.getOrganizations(filters);
            return array;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
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
