package ru.itmo.soa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.soa.entities.BalanceEntity;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {

    public static Long BALANCE_KEY = 1L;

    BalanceEntity getBalanceById(Long id);

    @Modifying
    @Transactional
    @Query("update BalanceEntity b set b.balanceKopecks = b.balanceKopecks + :newBalance where b.id = 1")
    void addBalance(@Param("newBalance") Long newBalance);

    @Modifying
    @Transactional
    @Query("update BalanceEntity b set b.balanceKopecks = b.balanceKopecks - :newBalance where b.id = 1")
    void removeBalance(@Param("newBalance") Long newBalance);
}
