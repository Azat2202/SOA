package ru.itmo.soa.services;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import ru.itmo.gen.model.Organization;
import ru.itmo.gen.model.OrganizationsDeleteByFullnamePostRequest;
import ru.itmo.soa.models.OrganizationEntity;
import ru.itmo.soa.repositories.OrganizationsRepository;

@RequestScoped
public class OrganizationService {

    @Inject
    private OrganizationsRepository organizationsRepository;

    @Inject
    private ModelMapper modelMapper;

    public Organization createOrganization(Organization organization) {
        OrganizationEntity organizationEntity = modelMapper.map(organization, OrganizationEntity.class);
        organizationEntity.setId(null);
        organizationEntity.setCreationDate(null);
        OrganizationEntity savedOrganizationEntity = organizationsRepository.save(organizationEntity);
        return modelMapper.map(savedOrganizationEntity, Organization.class);
    }

    public Response getOrganization(Integer id) {
        OrganizationEntity entity = organizationsRepository.findById(id.longValue());
        if (entity != null) {
            return Response.ok(modelMapper.map(entity, Organization.class)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response updateOrganization(Integer id, Organization organization) {
        OrganizationEntity existingEntity = organizationsRepository.findById(id.longValue());
        if (existingEntity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        existingEntity.setName(organization.getName());
        organization.getFullName().ifPresent(existingEntity::setFullName);
        existingEntity.getCoordinates().setX(organization.getCoordinates().getX());
        existingEntity.getCoordinates().setY(organization.getCoordinates().getY());
        existingEntity.setType(OrganizationEntity.TypeEnum.valueOf(organization.getType().getValue()));
        existingEntity.setAnnualTurnover(organization.getAnnualTurnover());
        organization.getEmployeesCount().ifPresent(existingEntity::setEmployeesCount);

        if (organization.getPostalAddress() != null) {
            existingEntity.getPostalAddress().setStreet(organization.getPostalAddress().getStreet());
            if (organization.getPostalAddress().getTown() != null) {
                organization.getPostalAddress().getTown().getName()
                        .ifPresent(existingEntity.getPostalAddress().getTown()::setName);
                existingEntity.getPostalAddress().getTown().setX(organization.getPostalAddress().getTown().getX());
                existingEntity.getPostalAddress().getTown().setY(organization.getPostalAddress().getTown().getY());
                existingEntity.getPostalAddress().getTown().setZ(organization.getPostalAddress().getTown().getZ());
            } else {
                existingEntity.getPostalAddress().setTown(null);
            }
        } else {
            existingEntity.setPostalAddress(null);
        }

        OrganizationEntity updatedEntity = organizationsRepository.save(existingEntity);
        Organization updatedOrganization = modelMapper.map(updatedEntity, Organization.class);
        return Response.ok(updatedOrganization).build();
    }

    public Response deleteOrganizationById(Integer id) {
        OrganizationEntity entity = organizationsRepository.findById(id.longValue());
        if (entity != null) {
            organizationsRepository.delete(entity);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response deleteOrganizationByFullname(OrganizationsDeleteByFullnamePostRequest fullname) {
        OrganizationEntity organizationEntity = organizationsRepository.findByFullName(fullname.getFullname());
        if (organizationEntity != null) {
            organizationsRepository.delete(organizationEntity);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Integer countOrganizationsByEmployeesCount(Integer quantity) {
        return organizationsRepository.countByEmployeesCount(quantity);
    }

    public Long countOrganizationsByAnnualTurnoverLess(Long annualTurnover) {
        return organizationsRepository.countByAnnualTurnoverBefore(annualTurnover + 1);
    }
}
