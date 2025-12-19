package ru.itmo.soa.endpoints;

import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFiltersFilter;
import ru.itmo.gen.model.Pagination;
import ru.itmo.soa.gen.GetByAnnualTurnoverRequest;
import ru.itmo.soa.gen.GetByType;
import ru.itmo.soa.gen.OrganizationWithPaging;
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
                        new Pagination()
                                .page(request.getPage())
                                .size(request.getSize()));
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
                OrganizationFiltersFilter.TypeEnum.fromValue(request.getType().value()),
                new Pagination()
                        .page(request.getPage())
                        .size(request.getSize()));
        OrganizationWithPaging result = toOrganizationWithPaging(organizationArray);
        return new JAXBElement<>(
                new QName(NAMESPACE_URI, "organizationWithPaging"),
                OrganizationWithPaging.class,
                result
        );
    }

    private static OrganizationWithPaging toOrganizationWithPaging(OrganizationArray organizationArray){
        OrganizationWithPaging result = new OrganizationWithPaging();
        if (organizationArray.getOrganizations() != null) {
            organizationArray.getOrganizations().stream()
                    .map(OrgDirectoryEndpoint::toGenOrganization)
                    .forEach(result.getOrganizations()::add);
        }
        result.setPage(defaultInt(organizationArray.getPage()));
        result.setSize(defaultInt(organizationArray.getSize()));
        result.setTotalCount(defaultInt(organizationArray.getTotalCount()));
        return result;
    }

    private static ru.itmo.soa.gen.Organization toGenOrganization(ru.itmo.gen.model.Organization source) {
        ru.itmo.soa.gen.Organization target = new ru.itmo.soa.gen.Organization();
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        target.setName(source.getName());
        if (source.getCoordinates() != null) {
            ru.itmo.soa.gen.Coordinates coords = new ru.itmo.soa.gen.Coordinates();
            coords.setX(source.getCoordinates().getX());
            coords.setY(source.getCoordinates().getY());
            target.setCoordinates(coords);
        }
        target.setCreationDate(toXmlDate(source.getCreationDate()));
        if (source.getAnnualTurnover() != null) {
            target.setAnnualTurnover(source.getAnnualTurnover());
        }
        target.setFullName(unwrap(source.getFullName()));
        target.setEmployeesCount(unwrap(source.getEmployeesCount()));
        if (source.getType() != null) {
            target.setType(toGenType(source.getType()));
        }
        if (source.getPostalAddress() != null) {
            ru.itmo.soa.gen.Address address = new ru.itmo.soa.gen.Address();
            address.setStreet(source.getPostalAddress().getStreet());
            if (source.getPostalAddress().getTown() != null) {
                ru.itmo.soa.gen.Location town = new ru.itmo.soa.gen.Location();
                town.setX(source.getPostalAddress().getTown().getX());
                town.setY(source.getPostalAddress().getTown().getY());
                town.setZ(source.getPostalAddress().getTown().getZ());
                town.setName(unwrap(source.getPostalAddress().getTown().getName()));
                address.setTown(town);
            }
            target.setPostalAddress(address);
        }
        return target;
    }

    private static ru.itmo.soa.gen.OrganizationType toGenType(ru.itmo.gen.model.Organization.TypeEnum type) {
        return switch (type) {
            case PUBLIC -> ru.itmo.soa.gen.OrganizationType.PUBLIC;
            case TRUST -> ru.itmo.soa.gen.OrganizationType.TRUST;
            case OPEN_JOINT_STOCK_COMPANY -> ru.itmo.soa.gen.OrganizationType.OPEN_JOINT_STOCK_COMPANY;
        };
    }

    private static <T> T unwrap(JsonNullable<T> value) {
        return value != null && value.isPresent() ? value.get() : null;
    }

    private static int defaultInt(Integer value) {
        return value == null ? 0 : value;
    }

    private static XMLGregorianCalendar toXmlDate(java.time.LocalDate date) {
        if (date == null) {
            return null;
        }
        try {
            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendarDate(
                            date.getYear(),
                            date.getMonthValue(),
                            date.getDayOfMonth(),
                            javax.xml.datatype.DatatypeConstants.FIELD_UNDEFINED
                    );
        } catch (Exception e) {
            return null;
        }
    }
}

