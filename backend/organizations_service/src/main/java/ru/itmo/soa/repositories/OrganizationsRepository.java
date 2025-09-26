package ru.itmo.soa.repositories;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.soa.models.OrganizationEntity;

import java.util.Optional;

public interface OrganizationsRepository extends JpaRepository<OrganizationEntity, Long>, OrganizationsRepositoryCustom {
    OrganizationEntity findByFullName(String fullName);

    Integer countByEmployeesCount(@Min(1) Integer employeesCount);

    Long countByAnnualTurnoverBefore(@NotNull @Min(1) Long annualTurnoverBefore);
}
