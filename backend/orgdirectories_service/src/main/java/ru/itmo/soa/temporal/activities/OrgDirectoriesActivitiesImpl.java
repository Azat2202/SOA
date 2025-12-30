package ru.itmo.soa.temporal.activities;


import io.temporal.spring.boot.ActivityImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itmo.activities.OrgDirectoriesActivities;
import ru.itmo.temporal_models.Organization;

import java.util.Random;

@Slf4j
@ActivityImpl(taskQueues = {"${spring.temporal.task-queue}"})
@Service
public class OrgDirectoriesActivitiesImpl implements OrgDirectoriesActivities {
    @Override
    public void processOrder(Organization organization) {
        log.info("Process organization");
        Random random = new Random();
        if(random.nextBoolean())
            throw new RuntimeException("random got true!");
    }

    @Override
    public void removeOrder(Organization organization) {
        log.info("Remove organization");
    }
}
