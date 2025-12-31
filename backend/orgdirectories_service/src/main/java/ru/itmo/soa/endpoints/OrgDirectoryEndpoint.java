package ru.itmo.soa.endpoints;

import com.google.protobuf.Timestamp;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.workflow.Async;
import jakarta.xml.bind.JAXBElement;

import javax.xml.namespace.QName;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFiltersFilter;
import ru.itmo.gen.model.Pagination;
import ru.itmo.soa.entities.BalanceEntity;
import ru.itmo.soa.gen.*;
import ru.itmo.soa.repository.BalanceRepository;
import ru.itmo.soa.service.OrgDirectoryService;
import ru.itmo.workflows.OrganizationWorkflow;

import java.time.Instant;
import java.util.UUID;

@Endpoint
@Component
@RequiredArgsConstructor
public class OrgDirectoryEndpoint {

    private static final String NAMESPACE_URI = "http://www.itmo.ru/soa/gen";

    @Value("${spring.temporal.task-queue}")
    private String taskQueue;

    private final OrgDirectoryService orgDirectoryService;
    private final BalanceRepository balanceRepository;
    private final WorkflowClient workflowClient;
    private final ModelMapper modelMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getByAnnualTurnoverRequest")
    @ResponsePayload
    public OrganizationWithPaging getOrganizationsInTurnoverRange(
            @RequestPayload GetByAnnualTurnoverRequest request) {
        OrganizationArray organizationArray = orgDirectoryService
                .getOrganizationsInRange(
                        request.getMinAnnualTurnover(),
                        request.getMaxAnnualTurnover(),
                        new Pagination()
                                .page(request.getPage())
                                .size(request.getSize()));
        OrganizationWithPaging result = toOrganizationWithPaging(organizationArray);
        return result;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getByType")
    @ResponsePayload
    public OrganizationWithPaging getOrganizationsByType(
            @RequestPayload GetByType request) {
        OrganizationArray organizationArray = orgDirectoryService.getOrganizationsByType(
                OrganizationFiltersFilter.TypeEnum.fromValue(request.getType().value()),
                new Pagination()
                        .page(request.getPage())
                        .size(request.getSize()));
        OrganizationWithPaging result = toOrganizationWithPaging(organizationArray);
        return result;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBalance")
    @ResponsePayload
    public Balance getBalance(
            @RequestPayload GetBalance request) {
        Long balanceKopecks = balanceRepository.getBalanceById(BalanceRepository.BALANCE_KEY).getBalanceKopecks();
        Balance balance = new Balance();
        balance.setBalance(balanceKopecks);
        return balance;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addBalance")
    @ResponsePayload
    public Balance addBalance(
            @RequestPayload AddBalance request) {
        balanceRepository.addBalance(request.getBalance());
        Long balanceKopecks = balanceRepository.getBalanceById(BalanceRepository.BALANCE_KEY).getBalanceKopecks();
        Balance response = new Balance();
        response.setBalance(balanceKopecks);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "organizationStatusRequest")
    @ResponsePayload
    public OrganizationStatusResponse getOrganizationStatus(
            @RequestPayload OrganizationStatusRequest request) {
        OrganizationWorkflow organizationWorkflow = workflowClient.newWorkflowStub(
                OrganizationWorkflow.class,
                request.getId().getId()
        );

        OrganizationStatusResponse response =  new OrganizationStatusResponse();
        response.setStatus(CreateOrganizatonStatus.fromValue(organizationWorkflow.getStatus().name()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createOrganizationPayment")
    @ResponsePayload
    public OrganizationPayment createOrganizationPayment(
            @RequestPayload CreateOrganizationPayment request) {
        String workflowId = UUID.randomUUID().toString();
        OrganizationWorkflow organizationWorkflow = workflowClient.newWorkflowStub(
                OrganizationWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue(taskQueue)
                        .setWorkflowId(workflowId).build()
        );

        WorkflowClient.start(organizationWorkflow::processOrder, toProtoOrganization(request.getRequest()));
        OrganizationPayment organizationPayment = new OrganizationPayment();
        CreateOrganizationWorkflowId workflowIdResponse = new CreateOrganizationWorkflowId();
        workflowIdResponse.setId(workflowId);
        organizationPayment.setId(workflowIdResponse);
        return organizationPayment;
    }


    private static OrganizationWithPaging toOrganizationWithPaging(OrganizationArray organizationArray) {
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

    private static com.google.protobuf.Timestamp toProtoTimestamp(Instant instant) {
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    private static ru.itmo.temporal_models.Organization toProtoOrganization(
            ru.itmo.soa.gen.Organization source
    ) {
        if (source == null) {
            return ru.itmo.temporal_models.Organization.getDefaultInstance();
        }
        ru.itmo.temporal_models.Organization.Builder builder =
                ru.itmo.temporal_models.Organization.newBuilder();
        if (source.getName() != null) {
            builder.setName(source.getName());
        }
        if (source.getCoordinates() != null) {
            ru.itmo.temporal_models.Coordinates.Builder coords = ru.itmo.temporal_models.Coordinates.newBuilder();
            coords.setX(source.getCoordinates().getX());
            coords.setY(source.getCoordinates().getY());
            builder.setCoordinates(coords.build());
        }
        builder.setAnnualTurnover(source.getAnnualTurnover());
        if (source.getFullName() != null) {
            builder.setFullName(source.getFullName());
        }
        if (source.getEmployeesCount() != null) {
            builder.setEmployeesCount(source.getEmployeesCount());
        }
        if (source.getType() != null) {
            try {
                builder.setType(
                        ru.itmo.temporal_models.OrganizationType
                                .valueOf(source.getType().name())
                );
            } catch (IllegalArgumentException ignored) {
                // unknown enum value -> leave default
            }
        }
        if (source.getPostalAddress() != null) {
            ru.itmo.temporal_models.Address.Builder address =
                    ru.itmo.temporal_models.Address.newBuilder();
            if (source.getPostalAddress().getStreet() != null) {
                address.setStreet(source.getPostalAddress().getStreet());
            }
            if (source.getPostalAddress().getTown() != null) {
                ru.itmo.temporal_models.Location.Builder town =
                        ru.itmo.temporal_models.Location.newBuilder();
                town.setX(source.getPostalAddress().getTown().getX());
                town.setY(source.getPostalAddress().getTown().getY());
                town.setZ(source.getPostalAddress().getTown().getZ());
                if (source.getPostalAddress().getTown().getName() != null) {
                    town.setName(source.getPostalAddress().getTown().getName());
                }
                address.setTown(town.build());
            }
            builder.setPostalAddress(address.build());
        }
        return builder.build();
    }


}

