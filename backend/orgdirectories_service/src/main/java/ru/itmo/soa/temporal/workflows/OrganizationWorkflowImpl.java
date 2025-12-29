package ru.itmo.soa.temporal.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.springframework.stereotype.Service;
import ru.itmo.activities.OrganizationActivities;
import ru.itmo.temporal_models.Organization;
import ru.itmo.workflows.OrganizationWorkflow;

import java.time.Duration;

@Service
@WorkflowImpl(taskQueues = "${spring.temporal.task-queue}")
public class OrganizationWorkflowImpl implements OrganizationWorkflow {

    @Override
    public void processOrder(Organization organization) {
        OrganizationActivities organizationActivities = Workflow.newActivityStub(
                OrganizationActivities.class,
                ActivityOptions.newBuilder()
                        .setStartToCloseTimeout(Duration.ofSeconds(30))
                        .build()
        );

        organizationActivities.processOrder(organization);
    }
}
