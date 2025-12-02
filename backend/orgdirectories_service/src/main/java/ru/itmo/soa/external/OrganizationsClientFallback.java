package ru.itmo.soa.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itmo.soa.model.OrganizationArray;
import ru.itmo.soa.model.OrganizationFilters;

@Slf4j
@Component
public class OrganizationsClientFallback implements OrganizationsClient {

    @Override
    public OrganizationArray getOrganizations(OrganizationFilters filters) {
        log.error("Fallback: Organizations service is unavailable");
        return new OrganizationArray();
    }
}

