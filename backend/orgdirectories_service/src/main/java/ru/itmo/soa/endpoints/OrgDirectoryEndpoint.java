package ru.itmo.soa.endpoints;

import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.itmo.soa.gen.GetByAnnualTurnoverRequest;
import ru.itmo.soa.gen.GetByType;
import ru.itmo.soa.gen.OrganizationWithPaging;
import ru.itmo.soa.model.Organization;
import ru.itmo.soa.model.OrganizationArray;
import ru.itmo.soa.model.OrganizationFiltersFilter;
import ru.itmo.soa.model.Pagination;
import ru.itmo.soa.service.OrgDirectoryService;

@Endpoint
@Component
@RequiredArgsConstructor
public class OrgDirectoryEndpoint {

    private static final String NAMESPACE_URI = "http://www.itmo.ru/soa/gen";

    private final OrgDirectoryService orgDirectoryService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getByAnnualTurnoverRequest")
    @ResponsePayload
    public JAXBElement<OrganizationWithPaging> getOrganizationsInTurnoverRange(
            @RequestPayload GetByAnnualTurnoverRequest request) {
        OrganizationArray organizationArray = orgDirectoryService
                .getOrganizationsInRange(
                        request.getMinAnnualTurnover(),
                        request.getMaxAnnualTurnover(),
                        new Pagination(
                                request.getPage(),
                                request.getSize()
                        ));
        OrganizationWithPaging result = toOrganizationWithPaging(organizationArray);
        return new JAXBElement<>(
                new QName(NAMESPACE_URI, "organizationWithPaging"),
                OrganizationWithPaging.class,
                result
        );
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getByType")
    @ResponsePayload
    public JAXBElement<OrganizationWithPaging> getOrganizationsByType(
            @RequestPayload GetByType request) {
        OrganizationArray organizationArray = orgDirectoryService.getOrganizationsByType(
                OrganizationFiltersFilter.TypeEnum.fromType(request.getType()),
                new Pagination(
                        request.getPage(),
                        request.getSize()
                ));
        OrganizationWithPaging result = toOrganizationWithPaging(organizationArray);
        return new JAXBElement<>(
                new QName(NAMESPACE_URI, "organizationWithPaging"),
                OrganizationWithPaging.class,
                result
        );
    }

    private static OrganizationWithPaging toOrganizationWithPaging(OrganizationArray organizationArray){
        OrganizationWithPaging result = new OrganizationWithPaging();
        result.getOrganizations().addAll(
                organizationArray.getOrganizations().stream()
                        .map(Organization::toGenOrganization)
                        .toList());
        result.setPage(organizationArray.getPage());
        result.setSize(organizationArray.getSize());
        result.setTotalCount(organizationArray.getTotalCount());
        return result;
    }
}

