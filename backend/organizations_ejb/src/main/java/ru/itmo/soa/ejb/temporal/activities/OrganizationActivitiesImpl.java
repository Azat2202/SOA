package ru.itmo.soa.ejb.temporal.activities;

import io.temporal.activity.ActivityInterface;
import ru.itmo.activities.OrganizationActivities;
import ru.itmo.soa.ejb.model.AddressEmbeddable;
import ru.itmo.soa.ejb.model.CoordinatesEmbeddable;
import ru.itmo.soa.ejb.model.LocationEmbeddable;
import ru.itmo.soa.ejb.model.OrganizationEntity;
import ru.itmo.soa.ejb.repository.OrganizationsRepository;
import ru.itmo.temporal_models.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class OrganizationActivitiesImpl implements OrganizationActivities {

    private final OrganizationsRepository organizationsRepository;

    public OrganizationActivitiesImpl(OrganizationsRepository organizationsRepository) {
        this.organizationsRepository = organizationsRepository;
    }

    @Override
    public void createOrganization(Organization organization) {
        organizationsRepository.save(
            organizationProtoToEntity(organization)
        );
    }

    @Override
    public void removeOrganization(Organization organization) {
        // not used
        System.out.println("organization removed");
    }

    private static OrganizationEntity organizationProtoToEntity(Organization organization) {
        return OrganizationEntity.builder()
                .id(organization.getId())
                .name(organization.getName())
                .coordinates(coordinatedProtoToEntity(organization.getCoordinates()))
                .creationDate(LocalDate.now())
                .annualTurnover(organization.getAnnualTurnover())
                .fullName(organization.hasFullName() ? organization.getFullName() : null)
                .employeesCount(organization.hasEmployeesCount() ? organization.getEmployeesCount() : null)
                .type(organizationTypeToEntity(organization.getType()))
                .postalAddress(organization.hasPostalAddress() ? addressProtoToEntity(organization.getPostalAddress()) : null)
                .build();
    }

    private static CoordinatesEmbeddable coordinatedProtoToEntity(Coordinates coordinates) {
        if (coordinates == null) {
            return null;
        }
        return CoordinatesEmbeddable.builder()
                .x(coordinates.getX())
                .y(coordinates.getY())
                .build();
    }

    private static LocationEmbeddable locationProtoToEntity(Location location) {
        if (location == null) {
            return null;
        }
        return LocationEmbeddable.builder()
                .x(location.getX())
                .y(location.getY())
                .z(location.getZ())
                .name(location.hasName() ? location.getName() : null)
                .build();
    }

    private static AddressEmbeddable addressProtoToEntity(Address address) {
        if (address == null) {
            return null;
        }
        return AddressEmbeddable.builder()
                .street(address.getStreet())
                .town(locationProtoToEntity(address.getTown()))
                .build();
    }

    private static OrganizationEntity.TypeEnum organizationTypeToEntity(OrganizationType type) {
        return switch (type) {
            case PUBLIC -> OrganizationEntity.TypeEnum.PUBLIC;
            case TRUST -> OrganizationEntity.TypeEnum.TRUST;
            case OPEN_JOINT_STOCK_COMPANY -> OrganizationEntity.TypeEnum.OPEN_JOINT_STOCK_COMPANY;
            default -> throw new IllegalArgumentException("Unsupported organization type: " + type);
        };
    }

}
