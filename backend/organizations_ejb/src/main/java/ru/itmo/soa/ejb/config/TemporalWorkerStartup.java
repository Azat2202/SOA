package ru.itmo.soa.ejb.config;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import ru.itmo.activities.OrganizationActivities;
import ru.itmo.soa.ejb.repository.OrganizationsRepository;
import ru.itmo.soa.ejb.temporal.activities.OrganizationActivitiesImpl;

import javax.sql.DataSource;

@Singleton
@Startup
public class TemporalWorkerStartup {
    private static String taskQueueName = "organizations";

    @Inject
    private OrganizationsRepository organizationsRepository;

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



        OrganizationActivities organizationActivities = new OrganizationActivitiesImpl(organizationsRepository);

        worker.registerActivitiesImplementations(organizationActivities);

        factory.start();
    }
}
