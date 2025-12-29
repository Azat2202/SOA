package ru.itmo.soa.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Controller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import ru.itmo.gen.model.*;
import ru.itmo.soa.gen.CreateOrganizationWorkflowId;
import ru.itmo.soa.gen.GetByAnnualTurnoverRequest;
import ru.itmo.soa.gen.GetByType;
import ru.itmo.soa.gen.OrganizationWithPaging;

import javax.xml.namespace.QName;
import java.util.UUID;

@RequiredArgsConstructor
public class OrgdirectoriesClient extends WebServiceGatewaySupport {

    private final ObjectMapper objectMapper;

    public OrganizationArray getOrganizationsInTurnoverRange(Long minAnnualTurnover,
                                                             Long maxAnnualTurnover,
                                                             Pagination pagination) {
        GetByAnnualTurnoverRequest request = new GetByAnnualTurnoverRequest();
        request.setMinAnnualTurnover(minAnnualTurnover.intValue());
        request.setMaxAnnualTurnover(maxAnnualTurnover.intValue());
        request.setPage(pagination.getPage());
        request.setSize(pagination.getSize());


        OrganizationWithPaging response = (ru.itmo.soa.gen.OrganizationWithPaging) getWebServiceTemplate()
                .marshalSendAndReceive(request);


        return objectMapper.convertValue(response, OrganizationArray.class);
    }

    public OrganizationArray getOrganizationsByType(String type, Pagination pagination) {
        GetByType request = new GetByType();
        request.setType(ru.itmo.soa.gen.OrganizationType.valueOf(type));
        request.setPage(pagination.getPage());
        request.setSize(pagination.getSize());

        OrganizationWithPaging response = (OrganizationWithPaging) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return objectMapper.convertValue(response, OrganizationArray.class);
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

    public OrganizationPayment createOrganization(Organization requestOrganization) {
        ru.itmo.soa.gen.Organization request = objectMapper.convertValue(requestOrganization, ru.itmo.soa.gen.Organization.class);

        ru.itmo.soa.gen.CreateOrganizationPayment createOrganizationPayment = new ru.itmo.soa.gen.CreateOrganizationPayment();
        createOrganizationPayment.setRequest(request);

        ru.itmo.soa.gen.OrganizationPayment response = (ru.itmo.soa.gen.OrganizationPayment) getWebServiceTemplate()
                .marshalSendAndReceive(createOrganizationPayment);
        OrganizationPayment organizationPayment = new OrganizationPayment();
        organizationPayment.setId(UUID.fromString(response.getId().getId()));
        return organizationPayment;
    }
}
