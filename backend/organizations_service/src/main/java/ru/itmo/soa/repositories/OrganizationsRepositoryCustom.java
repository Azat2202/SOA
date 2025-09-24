package ru.itmo.soa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itmo.gen.model.OrganizationFilters;
import ru.itmo.soa.models.OrganizationEntity;

public interface OrganizationsRepositoryCustom {
    Page<OrganizationEntity> findAllByFilters(OrganizationFilters filters, Pageable pageable);
} 