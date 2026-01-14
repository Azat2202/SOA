package ru.itmo.soa.temporal.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import org.springframework.stereotype.Service;
import ru.itmo.activities.OrgDirectoriesActivities;
import ru.itmo.activities.OrganizationActivities;
import ru.itmo.temporal_models.CreateOrganizationStatus;
import ru.itmo.temporal_models.MoneyKopecks;
import ru.itmo.temporal_models.Organization;
import ru.itmo.workflows.OrganizationWorkflow;

import java.time.Duration;

@Service
@WorkflowImpl(taskQueues = "${spring.temporal.task-queue}")
public class OrganizationWorkflowImpl implements OrganizationWorkflow {

    private final MoneyKopecks newOrganizationCost =
            MoneyKopecks
                    .newBuilder()
                    .setMoney(5000L)
                    .build();

    private CreateOrganizationStatus createOrganizationStatus = CreateOrganizationStatus.INITIATED;

    @Override
    public void processOrder(Organization organization) {
        Saga saga = new Saga(
                new Saga.Options.Builder()
                        .setParallelCompensation(true)
                        .build());
        try {
            OrgDirectoriesActivities orgDirectoriesActivities = Workflow.newActivityStub(
                    OrgDirectoriesActivities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(30))
                            .setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(1)
                                    .build())
                            .build()
            );
            OrganizationActivities organizationActivities = Workflow.newActivityStub(
                    OrganizationActivities.class,
                    ActivityOptions.newBuilder()
                            .setTaskQueue("organizations")
                            .setStartToCloseTimeout(Duration.ofSeconds(30))
                            .setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(1)
                                    .build())
                            .build()
            );

            Workflow.sleep(5000); // just for test :)
            orgDirectoriesActivities.takeMoney(newOrganizationCost);
            createOrganizationStatus = CreateOrganizationStatus.MONEY_TAKEN;
            Workflow.sleep(5000); // just for test :)
            saga.addCompensation(orgDirectoriesActivities::returnMoney, newOrganizationCost);

            organizationActivities.createOrganization(organization);
            createOrganizationStatus = CreateOrganizationStatus.ORGANIZATION_CREATED;
        } catch (Exception e) {
            saga.compensate();
            createOrganizationStatus = CreateOrganizationStatus.MONEY_RETURNING;
        }
    }

    @Override
    public CreateOrganizationStatus getStatus() {
        return createOrganizationStatus;
    }
}
