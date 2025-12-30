package ru.itmo.activities;

import io.temporal.activity.ActivityInterface;
import ru.itmo.temporal_models.MoneyKopecks;
import ru.itmo.temporal_models.Organization;

@ActivityInterface
public interface OrgDirectoriesActivities {
    void takeMoney(MoneyKopecks moneyKopecks);

    void returnMoney(MoneyKopecks moneyKopecks);
}
