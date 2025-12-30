package ru.itmo.activities;

import io.temporal.activity.ActivityInterface;
import ru.itmo.temporal_models.Organization;

@ActivityInterface
public interface OrgDirectoriesActivities {
    void processOrder(Organization organization);

    void removeOrder(Organization organization);
}
