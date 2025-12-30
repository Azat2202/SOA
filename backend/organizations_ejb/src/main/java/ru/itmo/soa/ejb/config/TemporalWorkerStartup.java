package ru.itmo.soa.ejb.config;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import ru.itmo.activities.OrganizationActivities;
import ru.itmo.soa.ejb.temporal.activities.OrganizationActivitiesImpl;

@Singleton
@Startup
public class TemporalWorkerStartup {
    private static String taskQueueName = "organizations";

    @PostConstruct
    public void initWorker() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget("temporal:7233")
                        .build()
        );
        WorkflowClient client = WorkflowClient.newInstance(service,
                WorkflowClientOptions.newBuilder().build());
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(taskQueueName);

        OrganizationActivities organizationActivities = new OrganizationActivitiesImpl();

        worker.registerActivitiesImplementations(organizationActivities);

        factory.start();
    }
}
