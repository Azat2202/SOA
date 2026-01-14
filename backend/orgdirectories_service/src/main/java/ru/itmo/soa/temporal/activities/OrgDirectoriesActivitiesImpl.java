package ru.itmo.soa.temporal.activities;


import com.google.type.Money;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.activities.OrgDirectoriesActivities;
import ru.itmo.soa.repository.BalanceRepository;
import ru.itmo.temporal_models.MoneyKopecks;
import ru.itmo.temporal_models.Organization;

import java.util.Random;

@Slf4j
@ActivityImpl(taskQueues = {"${spring.temporal.task-queue}"})
@Service
@RequiredArgsConstructor
public class OrgDirectoriesActivitiesImpl implements OrgDirectoriesActivities {

    private final BalanceRepository balanceRepository;

    @Override
    public void takeMoney(MoneyKopecks moneyKopecks) {
        if (balanceRepository.getBalanceById(BalanceRepository.BALANCE_KEY).getBalanceKopecks() < moneyKopecks.getMoney())
            throw new RuntimeException("Not enough money");
        balanceRepository.removeBalance(moneyKopecks.getMoney());
    }

    @Override
    public void returnMoney(MoneyKopecks moneyKopecks) {
        balanceRepository.addBalance(moneyKopecks.getMoney());
    }
}
