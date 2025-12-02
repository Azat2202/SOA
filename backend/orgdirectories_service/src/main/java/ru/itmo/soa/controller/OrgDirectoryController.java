package ru.itmo.soa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.soa.model.OrganizationArray;
import ru.itmo.soa.model.OrganizationFiltersFilter;
import ru.itmo.soa.model.Pagination;
import ru.itmo.soa.service.OrgDirectoryService;

@RestController
@RequestMapping("/api/orgdirectory")
@RequiredArgsConstructor
public class OrgDirectoryController {

    private final OrgDirectoryService orgDirectoryService;

    @PostMapping("/filter/turnover/{min-annual-turnover}/{max-annual-turnover}")
    public ResponseEntity<OrganizationArray> getOrganizationsInTurnoverRange(
            @PathVariable("min-annual-turnover") Integer min,
            @PathVariable("max-annual-turnover") Integer max,
            @RequestBody(required = false) Pagination pagination) {
        OrganizationArray result = orgDirectoryService.getOrganizationsInRange(min, max, pagination);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/filter/type/{type}")
    public ResponseEntity<OrganizationArray> getOrganizationsByType(
            @PathVariable("type") String type,
            @RequestBody(required = false) Pagination pagination) {
        OrganizationArray result = orgDirectoryService.getOrganizationsByType(
                OrganizationFiltersFilter.TypeEnum.valueOf(type), pagination);
        return ResponseEntity.ok(result);
    }
}

