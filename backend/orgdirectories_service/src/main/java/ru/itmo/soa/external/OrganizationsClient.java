package ru.itmo.soa.external;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.soa.model.OrganizationArray;
import ru.itmo.soa.model.OrganizationFilters;

@ApplicationScoped
public class OrganizationsClient {
    public OrganizationArray getOrganizations(OrganizationFilters filters) {
//        return new OrganizationArray();
        String serviceUrl = "http://localhost:8081/organizations";
        String methodUrl = serviceUrl + "/filter";

        try {
            Client client = ClientBuilder.newClient();
            Entity<OrganizationFilters> entity = Entity.entity(filters, MediaType.APPLICATION_JSON);

            System.out.println(entity);

            Response response = client.target(methodUrl).request(MediaType.APPLICATION_JSON_TYPE).post(entity);

            if (response.getStatus() != 200) {
                throw new WebApplicationException(response);
            }

            OrganizationArray resp = response.readEntity(OrganizationArray.class);
            client.close();

            return resp;
        } catch (ProcessingException e) {
            System.out.println("Exception" + e.getMessage());
            return null;
        }
    }
}
