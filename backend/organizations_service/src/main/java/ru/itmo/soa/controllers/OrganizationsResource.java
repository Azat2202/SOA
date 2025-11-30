package ru.itmo.soa.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.gen.model.*;
import ru.itmo.soa.services.OrganizationFilterService;
import ru.itmo.soa.services.OrganizationService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationsResource {

    @Inject
    private OrganizationFilterService organizationFilterService;

    @Inject
    private OrganizationService organizationService;

    @POST
    public Response createOrganization(Organization organization) {
        Organization created = organizationService.createOrganization(organization);
        return Response.ok(created).build();
    }

    @POST
    @Path("/filter")
    public Response filterOrganizations(OrganizationFilters organizationFilters) {
        return organizationFilterService.organizationsFilterPost(organizationFilters);
    }

    @GET
    @Path("/{id}")
    public Response getOrganization(@PathParam("id") Integer id) {
        return organizationService.getOrganization(id);
    }

    @PUT
    @Path("/{id}")
    public Response updateOrganization(@PathParam("id") Integer id, Organization organization) {
        return organizationService.updateOrganization(id, organization);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrganization(@PathParam("id") Integer id) {
        return organizationService.deleteOrganizationById(id);
    }

    @POST
    @Path("/delete-by-fullname")
    public Response deleteOrganizationByFullname(OrganizationsDeleteByFullnamePostRequest fullname) {
        return organizationService.deleteOrganizationByFullname(fullname);
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

