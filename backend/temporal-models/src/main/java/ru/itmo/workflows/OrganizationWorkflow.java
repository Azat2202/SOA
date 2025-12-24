package ru.itmo.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import ru.itmo.temporal_models.Organization;

@WorkflowInterface
public interface OrganizationWorkflow {
    @WorkflowMethod
    void processOrder(Organization organization);
}
