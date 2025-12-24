package ru.itmo.soa.clients;

import jakarta.xml.bind.JAXBElement;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Controller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import ru.itmo.gen.model.*;
import ru.itmo.soa.gen.GetByAnnualTurnoverRequest;
import ru.itmo.soa.gen.GetByType;
import ru.itmo.soa.gen.OrganizationWithPaging;

import javax.xml.namespace.QName;

public class OrgdirectoriesClient extends WebServiceGatewaySupport {

    public OrganizationArray getOrganizationsInTurnoverRange(Long minAnnualTurnover,
                                                             Long maxAnnualTurnover,
                                                             Pagination pagination) {
        GetByAnnualTurnoverRequest request = new GetByAnnualTurnoverRequest();
        request.setMinAnnualTurnover(minAnnualTurnover.intValue());
        request.setMaxAnnualTurnover(maxAnnualTurnover.intValue());
        request.setPage(pagination.getPage());
        request.setSize(pagination.getSize());


        OrganizationWithPaging response = ((JAXBElement<OrganizationWithPaging>) getWebServiceTemplate()
                .marshalSendAndReceive(request)).getValue();


        OrganizationArray organizationArray = new OrganizationArray();
        organizationArray.setOrganizations(
                response.getOrganizations().stream().map(OrgdirectoriesClient::toOrganizationFromGen).toList()
        );
        organizationArray.setPage(response.getPage());
        organizationArray.setSize(response.getSize());
        return organizationArray;
    }

    public OrganizationArray getOrganizationsByType(String type, Pagination pagination) {
        GetByType request = new GetByType();
        request.setType(ru.itmo.soa.gen.OrganizationType.valueOf(type));
        request.setPage(pagination.getPage());
        request.setSize(pagination.getSize());

        OrganizationWithPaging response = ((JAXBElement<OrganizationWithPaging>) getWebServiceTemplate()
                .marshalSendAndReceive(request)).getValue();

        OrganizationArray organizationArray = new OrganizationArray();
        organizationArray.setOrganizations(
                response.getOrganizations().stream().map(OrgdirectoriesClient::toOrganizationFromGen).toList()
        );
        organizationArray.setPage(response.getPage());
        organizationArray.setSize(response.getSize());
        return organizationArray;
    }

    public Balance getBalance() {
        ru.itmo.soa.gen.GetBalance request = new ru.itmo.soa.gen.GetBalance();

        ru.itmo.soa.gen.Balance response = (ru.itmo.soa.gen.Balance) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        Balance balance = new Balance();
        balance.setBalance(response.getBalance());
        return balance;
    }

    public Balance addBalance(Balance requestBalance) {
        ru.itmo.soa.gen.AddBalance request = new ru.itmo.soa.gen.AddBalance();
        request.setBalance(requestBalance.getBalance());

        ru.itmo.soa.gen.Balance response = (ru.itmo.soa.gen.Balance) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        Balance balance = new Balance();
        balance.setBalance(response.getBalance());
        return balance;
    }

    private static Organization toOrganizationFromGen(ru.itmo.soa.gen.Organization organization) {
        Organization org = new Organization();
        org.setId(organization.getId());
        org.setName(organization.getName());
        org.setAnnualTurnover(organization.getAnnualTurnover());
        org.setCreationDate(organization.getCreationDate().toGregorianCalendar().toZonedDateTime().toLocalDate());
        Integer employeesCount = organization.getEmployeesCount();
        org.setEmployeesCount(
                employeesCount == null ? JsonNullable.undefined() : JsonNullable.of(employeesCount)
        );
        String fullName = organization.getFullName();
        org.setFullName(fullName == null ? JsonNullable.undefined() : JsonNullable.of(fullName));
        org.setCoordinates(toCoordinatesFromGen(organization.getCoordinates()));
        org.setPostalAddress(toAddressFromGen(organization.getPostalAddress()));
        org.setAnnualTurnover(organization.getAnnualTurnover());
        org.setType(toTypeEnum(organization.getType()));
        return org;
    }

    private static Coordinates toCoordinatesFromGen(ru.itmo.soa.gen.Coordinates coordinates) {
        Coordinates coord = new Coordinates();
        coord.setX(coordinates.getX());
        coord.setY(coordinates.getY());
        return coord;
    }

    private static Address toAddressFromGen(ru.itmo.soa.gen.Address address) {
        if (address == null) {
            return null;
        }
        Address addr = new Address();
        addr.setStreet(address.getStreet());
        addr.setTown(toLocationFromGen(address.getTown()));
        return addr;
    }

    private static Location toLocationFromGen(ru.itmo.soa.gen.Location location) {
        Location loc = new Location();
        loc.setName(JsonNullable.of(location.getName()));
        loc.setX(location.getX());
        loc.setY(location.getY());
        loc.setZ(location.getZ());
        return loc;
    }

    private static Organization.TypeEnum toTypeEnum(ru.itmo.soa.gen.OrganizationType organizationType) {
        return Organization.TypeEnum.fromValue(organizationType.value());
    }
}
