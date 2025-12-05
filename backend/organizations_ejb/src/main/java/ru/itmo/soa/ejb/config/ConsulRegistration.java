package ru.itmo.soa.ejb.config;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConsulRegistration {

    private static final Logger log = Logger.getLogger(ConsulRegistration.class.getName());

    private ConsulClient consulClient;
    private String serviceId;

    @PostConstruct
    public void register() {
        String consulHost = System.getenv().getOrDefault("CONSUL_HOST", "localhost");
        Integer consulPort = Integer.parseInt(System.getenv().getOrDefault("CONSUL_PORT", "8500"));
        String serviceName = System.getenv().getOrDefault("SERVICE_NAME", "organizations-service");
        Integer servicePort = Integer.parseInt(System.getenv().getOrDefault("SERVICE_PORT", "8080"));
        String hostAddress = System.getenv("HOST_ADDRESS");
        serviceId = System.getenv().getOrDefault("SERVICE_ID", serviceName + "-1");

        String consulUrl = String.format("http://%s:%d", consulHost, consulPort);

        try {
            this.consulClient = new ConsulClient(consulUrl);
            NewService newService = new NewService();
            newService.setId(serviceId);
            newService.setName(serviceName);
            newService.setAddress(hostAddress);
            newService.setPort(servicePort);

            NewService.Check healthCheck = new NewService.Check();
            healthCheck.setHttp(String.format("http://%s:%d/organizations/health", hostAddress, servicePort));
            healthCheck.setInterval("10s");
            healthCheck.setTimeout("3s");
            healthCheck.setDeregisterCriticalServiceAfter("30s");

            newService.setCheck(healthCheck);

            consulClient.agentServiceRegister(newService);
            log.info(String.format("Successfully registered service '%s' (ID: %s) in Consul at %s",
                    serviceName, serviceId, consulUrl));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error registering service in Consul", e);
        }
    }

    @PreDestroy
    public void deregister() {
        consulClient.agentServiceDeregister(serviceId);
        log.info(String.format("Successfully deregistered service '%s' from Consul", serviceId));
    }
}

