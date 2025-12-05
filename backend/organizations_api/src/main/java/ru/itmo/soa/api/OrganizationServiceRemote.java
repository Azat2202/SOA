package ru.itmo.soa.api;

import jakarta.ejb.Remote;
import ru.itmo.gen.model.Organization;
import ru.itmo.gen.model.OrganizationsDeleteByFullnamePostRequest;

@Remote
public interface OrganizationServiceRemote {

    Organization createOrganization(Organization organization);

    Organization getOrganization(Integer id);

    Organization updateOrganization(Integer id, Organization organization);

    boolean deleteOrganizationById(Integer id);

    boolean deleteOrganizationByFullname(OrganizationsDeleteByFullnamePostRequest fullname);

    Integer countOrganizationsByEmployeesCount(Integer quantity);

    Long countOrganizationsByAnnualTurnoverLess(Long annualTurnover);
}

