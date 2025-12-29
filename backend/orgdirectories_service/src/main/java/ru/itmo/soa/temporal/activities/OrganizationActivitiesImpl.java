package ru.itmo.soa.temporal.activities;


import io.temporal.spring.boot.ActivityImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.activities.OrganizationActivities;
import ru.itmo.temporal_models.Organization;

@Slf4j
@ActivityImpl(taskQueues = {"${spring.temporal.task-queue}"})
@Service
public class OrganizationActivitiesImpl implements OrganizationActivities {
    @Override
    public void processOrder(Organization organization) {
        log.info("Process organization");
    }
}
