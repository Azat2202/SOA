package ru.itmo.soa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.gen.api.OrganizationsApi;
import ru.itmo.gen.model.*;
import lombok.RequiredArgsConstructor;
import ru.itmo.soa.services.OrganizationFilterService;
import ru.itmo.soa.services.OrganizationService;

@RestController
@RequiredArgsConstructor
public class OrganizationsController implements OrganizationsApi {

    private final OrganizationFilterService organizationFilterService;
    private final OrganizationService organizationService;

    @Override
    public ResponseEntity<Organization> organizationsPost(Organization organization) {
        return ResponseEntity.ok(organizationService.createOrganization(organization));
    }

    @Override
    public ResponseEntity<OrganizationArray> organizationsFilterPost(OrganizationFilters organizationFilters) {
        return organizationFilterService.organizationsFilterPost(organizationFilters);
    }

    @Override
    public ResponseEntity<Organization> organizationsIdPut(Integer id, Organization organization) {
        return organizationService.updateOrganization(id, organization);
    }

    @Override
    public ResponseEntity<Void> organizationsIdDelete(Integer id) {
        return organizationService.deleteOrganizationById(id);
    }

    @Override
    public ResponseEntity<Void> organizationsDeleteByFullnamePost(OrganizationsDeleteByFullnamePostRequest fullname) {
        return organizationService.deleteOrganizationByFullname(fullname);
    }

    @Override
    public ResponseEntity<OrganizationsQuantityByEmployeesGet200Response> organizationsQuantityByEmployeesGet(Integer quantity) {
        var count = organizationService.countOrganizationsByEmpoyeesCount(quantity);
        var response = new OrganizationsQuantityByEmployeesGet200Response();
        response.setCount(count);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<OrganizationsQuantityByEmployeesGet200Response> organizationsQuantityByTurnoverGet(Long maxTurnover) {
        var count = organizationService.countOrganizationsByAnnualTurnoverLess(maxTurnover);
        var response = new OrganizationsQuantityByEmployeesGet200Response();
        response.setCount(count.intValue());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Organization> organizationsIdGet(Integer id) {
        return organizationService.getOrganization(id);
    }
}
