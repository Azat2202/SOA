package ru.itmo.soa.temporal.activities;


import io.temporal.spring.boot.ActivityImpl;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.activities.OrganizationActivities;
import ru.itmo.temporal_models.Organization;

@Slf4j
@ActivityImpl(taskQueues = "organizations")
public class OrganizationActivitiesImpl implements OrganizationActivities {
    @Override
    public void processOrder(Organization organization) {
        log.info("Process organization");
    }
}
