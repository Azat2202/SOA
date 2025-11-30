package ru.itmo.soa.api;

import jakarta.ejb.Local;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFilters;

@Local
public interface OrganizationFilterServiceLocal {

    OrganizationArray filterOrganizations(OrganizationFilters organizationFilters);
}

