package ru.itmo.soa.temporal.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Saga;
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
        Saga saga = new Saga(
                new Saga.Options.Builder()
                        .setParallelCompensation(true)
                        .build());
        try {
            OrganizationActivities organizationActivities = Workflow.newActivityStub(
                    OrganizationActivities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(30))
                            .setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(1)
                                    .build())
                            .build()
            );

            organizationActivities.processOrder(organization);
            saga.addCompensation(organizationActivities::removeOrder, organization);
            organizationActivities.processOrder(organization);
            saga.addCompensation(organizationActivities::removeOrder, organization);
            organizationActivities.processOrder(organization);
            saga.addCompensation(organizationActivities::removeOrder, organization);
        } catch (Exception e) {
            saga.compensate();
        }
    }
}
