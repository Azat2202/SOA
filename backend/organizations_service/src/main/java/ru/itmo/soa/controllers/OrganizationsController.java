package ru.itmo.soa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.gen.api.OrganizationsApi;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFilters;
import ru.itmo.gen.model.OrganizationFiltersSortInner;
import ru.itmo.soa.models.OrganizationEntity;
import ru.itmo.soa.repositories.OrganizationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.itmo.gen.model.Organization;
import ru.itmo.gen.model.Coordinates;
import ru.itmo.gen.model.Address;
import ru.itmo.gen.model.Location;
import org.modelmapper.ModelMapper;
import org.openapitools.jackson.nullable.JsonNullable;
import ru.itmo.soa.services.OrganizationFilterService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrganizationsController implements OrganizationsApi {

    private final OrganizationFilterService organizationFilterService;

    @Override
    public ResponseEntity<OrganizationArray> organizationsFilterPost(OrganizationFilters organizationFilters) {
        return organizationFilterService.organizationsFilterPost(organizationFilters);
    }
}
