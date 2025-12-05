package ru.itmo.soa.api;

import jakarta.ejb.Remote;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFilters;

@Remote
public interface OrganizationFilterServiceRemote {

    OrganizationArray filterOrganizations(OrganizationFilters organizationFilters);
}

