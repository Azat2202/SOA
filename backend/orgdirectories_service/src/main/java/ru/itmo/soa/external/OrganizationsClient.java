package ru.itmo.soa.external;

import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFilters;

import javax.ejb.Stateless;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
public class OrganizationsClient {
    public OrganizationArray getOrganizations(OrganizationFilters filters) {
        String serviceUrl = "http://localhost:8081/organizations";
        String methodUrl = serviceUrl + "/filter";

        try {
            Client client = ClientBuilder.newClient();
            Entity<OrganizationFilters> entity = Entity.entity(filters, MediaType.APPLICATION_JSON);

            Response response = client.target(methodUrl).request(MediaType.APPLICATION_JSON_TYPE).post(entity);

            if (response.getStatus() != 200) {
                throw new WebApplicationException(response);
            }

            OrganizationArray resp = response.readEntity(OrganizationArray.class);
            client.close();

            return resp;
        } catch (ProcessingException e) {
            return null;
        }
    }
}
