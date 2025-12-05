package ru.itmo.soa.controllers;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.gen.model.*;
import ru.itmo.soa.api.OrganizationFilterServiceLocal;
import ru.itmo.soa.api.OrganizationServiceLocal;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class OrganizationsResource {

    @EJB(lookup = "java:app/organizations_ejb/OrganizationFilterServiceBean!ru.itmo.soa.api.OrganizationFilterServiceLocal")
    private OrganizationFilterServiceLocal organizationFilterService;

    @EJB(lookup = "java:app/organizations_ejb/OrganizationServiceBean!ru.itmo.soa.api.OrganizationServiceLocal")
    private OrganizationServiceLocal organizationService;

    @POST
    public Response createOrganization(Organization organization) {
        Organization created = organizationService.createOrganization(organization);
        return Response.ok(created).build();
    }

    @POST
    @Path("/filter")
    public Response filterOrganizations(OrganizationFilters organizationFilters) {
        OrganizationArray result = organizationFilterService.filterOrganizations(organizationFilters);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrganization(@PathParam("id") Integer id) {
        Organization organization = organizationService.getOrganization(id);
        if (organization != null) {
            return Response.ok(organization).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateOrganization(@PathParam("id") Integer id, Organization organization) {
        Organization updated = organizationService.updateOrganization(id, organization);
        if (updated != null) {
            return Response.ok(updated).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrganization(@PathParam("id") Integer id) {
        boolean deleted = organizationService.deleteOrganizationById(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/delete-by-fullname")
    public Response deleteOrganizationByFullname(OrganizationsDeleteByFullnamePostRequest fullname) {
        boolean deleted = organizationService.deleteOrganizationByFullname(fullname);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/quantity-by-employees")
    public Response countByEmployees(@QueryParam("quantity") Integer quantity) {
        Integer count = organizationService.countOrganizationsByEmployeesCount(quantity);
        OrganizationsQuantityByEmployeesGet200Response response = new OrganizationsQuantityByEmployeesGet200Response();
        response.setCount(count);
        return Response.ok(response).build();
    }

    @GET
    @Path("/quantity-by-turnover")
    public Response countByTurnover(@QueryParam("maxTurnover") Long maxTurnover) {
        Long count = organizationService.countOrganizationsByAnnualTurnoverLess(maxTurnover);
        OrganizationsQuantityByEmployeesGet200Response response = new OrganizationsQuantityByEmployeesGet200Response();
        response.setCount(count.intValue());
        return Response.ok(response).build();
    }
}
