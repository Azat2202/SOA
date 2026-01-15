package ru.itmo.soa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.gen.api.OrgDirectoriesApi;
import ru.itmo.gen.model.*;
import ru.itmo.soa.clients.OrgdirectoriesClient;

@RestController
@RequiredArgsConstructor
public class OrgdirectoriesController implements OrgDirectoriesApi {

    private final OrgdirectoriesClient orgdirectoriesClient;

    @Override
    public ResponseEntity<OrganizationArray> orgdirectoryFilterTurnoverMinAnnualTurnoverMaxAnnualTurnoverPost(
            Long minAnnualTurnover,
            Long maxAnnualTurnover,
            Pagination pagination) {
        return ResponseEntity.ok(
                orgdirectoriesClient.getOrganizationsInTurnoverRange(minAnnualTurnover, maxAnnualTurnover, pagination)
        );
    }

    @Override
    public ResponseEntity<OrganizationArray> orgdirectoryFilterTypeTypePost(String type, Pagination pagination) {
        return ResponseEntity.ok(
                orgdirectoriesClient.getOrganizationsByType(type, pagination)
        );
    }

    @Override
    public ResponseEntity<Balance> orgdirectoryBalanceGet() {
        return ResponseEntity.ok(
                orgdirectoriesClient.getBalance()
        );
    }

    @Override
    public ResponseEntity<Balance> orgdirectoryBalancePost(Balance balance) {
        return ResponseEntity.ok(
            orgdirectoriesClient.addBalance(balance)
        );
    }

    @Override
    public ResponseEntity<OrganizationPayment> orgdirectoryOrganizationPost(Organization organization) {
        return ResponseEntity.ok(
                orgdirectoriesClient.createOrganization(organization)
        );
    }

    @Override
    public ResponseEntity<OrganizationStatus> orgdirectoryOrganizationGet(String uuid) {
        return ResponseEntity.ok(
                orgdirectoriesClient.getOrganizationStatus(uuid)
        );
    }
}
