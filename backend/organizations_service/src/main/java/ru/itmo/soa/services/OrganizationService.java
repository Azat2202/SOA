package ru.itmo.soa.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.itmo.gen.model.Organization;
import ru.itmo.gen.model.OrganizationsDeleteByFullnamePostRequest;
import ru.itmo.soa.models.OrganizationEntity;
import ru.itmo.soa.repositories.OrganizationsRepository;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationsRepository organizationsRepository;
    private final ModelMapper modelMapper;

    public Organization createOrganization(Organization organization) {
        OrganizationEntity organizationEntity = modelMapper.map(organization, OrganizationEntity.class);
        organizationEntity.setId(null);
        organizationEntity.setCreationDate(null);
        OrganizationEntity savedOrganizationEntity = organizationsRepository.save(organizationEntity);
        return modelMapper.map(savedOrganizationEntity, Organization.class);
    }

    public ResponseEntity<Organization> getOrganization(Integer id) {
        return organizationsRepository
                .findById(id.longValue())
                .map(organizationEntity -> ResponseEntity.ok(modelMapper.map(organizationEntity, Organization.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Organization> updateOrganization(Integer id, Organization organization){
        return organizationsRepository.findById(id.longValue())
                .map(existingEntity -> {
                    existingEntity.setName(organization.getName());
                    organization.getFullName()
                            .ifPresent(existingEntity::setFullName);
                    existingEntity.getCoordinates().setX(organization.getCoordinates().getX());
                    existingEntity.getCoordinates().setY(organization.getCoordinates().getY());
                    existingEntity.setType(OrganizationEntity.TypeEnum.valueOf(organization.getType().getValue()));
                    existingEntity.setAnnualTurnover(organization.getAnnualTurnover());
                    organization.getEmployeesCount()
                            .ifPresent(existingEntity::setEmployeesCount);
                    if (organization.getPostalAddress() != null) {
                        existingEntity.getPostalAddress().setStreet(organization.getPostalAddress().getStreet());
                    } else {
                        existingEntity.setPostalAddress(null);
                    }
                    if (organization.getPostalAddress() != null && organization.getPostalAddress().getTown() != null) {
                        organization.getPostalAddress().getTown().getName()
                                .ifPresent(existingEntity.getPostalAddress().getTown()::setName);
                        existingEntity.getPostalAddress().getTown().setX(organization.getPostalAddress().getTown().getX());
                        existingEntity.getPostalAddress().getTown().setY(organization.getPostalAddress().getTown().getY());
                        existingEntity.getPostalAddress().getTown().setZ(organization.getPostalAddress().getTown().getZ());
                    } else {
                        existingEntity.getPostalAddress().setTown(null);
                    }
                    OrganizationEntity updatedEntity = organizationsRepository.save(existingEntity);
                    Organization updatedOrganization = modelMapper.map(updatedEntity, Organization.class);
                    return ResponseEntity.ok(updatedOrganization);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteOrganizationById(Integer id){
        if (organizationsRepository.existsById(id.longValue())) {
            organizationsRepository.deleteById(id.longValue());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteOrganizationByFullname(OrganizationsDeleteByFullnamePostRequest fullname){
        var organizationEntity = organizationsRepository.findByFullName(fullname.getFullname());
        if (organizationEntity != null) {
            organizationsRepository.delete(organizationEntity);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public Integer countOrganizationsByEmpoyeesCount(Integer quantity){
        return organizationsRepository.countByEmployeesCount(quantity);
    }

    public Long countOrganizationsByAnnualTurnoverLess(Long annualTurnover){
        return organizationsRepository.countByAnnualTurnoverBefore(annualTurnover + 1); // включительно
    }


}
