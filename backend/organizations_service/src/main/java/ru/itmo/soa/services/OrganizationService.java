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
                    modelMapper.map(organization, existingEntity);
                    existingEntity.setId(id.longValue());
                    existingEntity.setCreationDate(organization.getCreationDate());
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
