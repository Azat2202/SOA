package ru.itmo.soa.ejb.temporal.activities;

import io.temporal.activity.ActivityInterface;
import ru.itmo.activities.OrganizationActivities;
import ru.itmo.temporal_models.Organization;

public class OrganizationActivitiesImpl implements OrganizationActivities {
    @Override
    public void createOrganization(Organization organization) {
        System.out.println("organization created");
    }

    @Override
    public void removeOrganization(Organization organization) {
        System.out.println("organization removed");
    }
}
