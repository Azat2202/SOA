package ru.itmo.soa.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.itmo.gen.model.Organization;
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
}
