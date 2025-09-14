package ru.itmo.soa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.gen.api.OrganizationsApi;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFilters;

@RestController
public class OrganizationsController implements OrganizationsApi {
    @Override
    public ResponseEntity<OrganizationArray> organizationsFilterPost(OrganizationFilters organizationFilters) {
        return OrganizationsApi.super.organizationsFilterPost(organizationFilters);
    }
}
