package ru.itmo.soa.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFilters;

@FeignClient(name = "organizations-service", url = "${organizations.service.url}")
public interface OrganizationsClient {

    @PostMapping("/filter")
    OrganizationArray getOrganizations(@RequestBody OrganizationFilters filters);
}
