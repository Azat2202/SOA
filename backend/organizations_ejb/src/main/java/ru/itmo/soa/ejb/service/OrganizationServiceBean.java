package ru.itmo.soa.ejb.service;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import ru.itmo.gen.model.Organization;
import ru.itmo.gen.model.OrganizationsDeleteByFullnamePostRequest;
import ru.itmo.soa.api.OrganizationServiceLocal;
import ru.itmo.soa.api.OrganizationServiceRemote;
import ru.itmo.soa.ejb.model.OrganizationEntity;
import ru.itmo.soa.ejb.repository.OrganizationsRepository;

@Stateless
public class OrganizationServiceBean implements OrganizationServiceLocal, OrganizationServiceRemote {

    @EJB
    private OrganizationsRepository organizationsRepository;

    @Inject
    private ModelMapper modelMapper;

    @Override
    public Organization createOrganization(Organization organization) {
        OrganizationEntity organizationEntity = modelMapper.map(organization, OrganizationEntity.class);
        organizationEntity.setId(null);
        organizationEntity.setCreationDate(null);
        OrganizationEntity savedOrganizationEntity = organizationsRepository.save(organizationEntity);
        return modelMapper.map(savedOrganizationEntity, Organization.class);
    }

    @Override
    public Organization getOrganization(Integer id) {
        OrganizationEntity entity = organizationsRepository.findById(id.longValue());
        if (entity != null) {
            return modelMapper.map(entity, Organization.class);
        }
        return null;
    }

    @Override
    public Organization updateOrganization(Integer id, Organization organization) {
        OrganizationEntity existingEntity = organizationsRepository.findById(id.longValue());
        if (existingEntity == null) {
            return null;
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
        return modelMapper.map(updatedEntity, Organization.class);
    }

    @Override
    public boolean deleteOrganizationById(Integer id) {
        OrganizationEntity entity = organizationsRepository.findById(id.longValue());
        if (entity != null) {
            organizationsRepository.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOrganizationByFullname(OrganizationsDeleteByFullnamePostRequest fullname) {
        OrganizationEntity organizationEntity = organizationsRepository.findByFullName(fullname.getFullname());
        if (organizationEntity != null) {
            organizationsRepository.delete(organizationEntity);
            return true;
        }
        return false;
    }

    @Override
    public Integer countOrganizationsByEmployeesCount(Integer quantity) {
        return organizationsRepository.countByEmployeesCount(quantity);
    }

    @Override
    public Long countOrganizationsByAnnualTurnoverLess(Long annualTurnover) {
        return organizationsRepository.countByAnnualTurnoverBefore(annualTurnover + 1);
    }
}
