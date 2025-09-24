package ru.itmo.soa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.soa.models.OrganizationEntity;

public interface OrganizationsRepository extends JpaRepository<OrganizationEntity, Long> {
}
