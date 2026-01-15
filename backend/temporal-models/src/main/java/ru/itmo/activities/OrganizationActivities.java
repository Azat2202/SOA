package ru.itmo.activities;

import io.temporal.activity.ActivityInterface;
import ru.itmo.temporal_models.Organization;

@ActivityInterface
public interface OrganizationActivities {
    void createOrganization(Organization organization);

    void removeOrganization(Organization organization);
}
