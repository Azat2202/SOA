package ru.itmo.soa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.soa.entities.BalanceEntity;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {

    public static Long BALANCE_KEY = 1L;

    BalanceEntity getBalanceById(Long id);
}
