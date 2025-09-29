package ru.itmo.soa;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ru.itmo.soa.model.OrganizationArray;
import ru.itmo.soa.model.OrganizationFiltersFilter;
import ru.itmo.soa.model.Pagination;
import ru.itmo.soa.service.OrgDirectoryService;

@Path("/orgdirectory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrgDirectoryResource {
    @Inject
    OrgDirectoryService orgDirectoryService;

    @POST
    @Path("/filter/turnover/{min-annual-turnover}/{max-annual-turnover}")
    public OrganizationArray getOrganizationsInTurnoverRange(
            @PathParam("min-annual-turnover") Integer min,
            @PathParam("max-annual-turnover") Integer max,
            Pagination pagination) {
        return orgDirectoryService.getOrganizationsInRange(min, max, pagination);
        }


    @POST
    @Path("/filter/type/{type}")
    public OrganizationArray getOrganizationsByType(
            @PathParam("type") String type,
            Pagination pagination
    ) {
        return orgDirectoryService.getOrganizationsByType(OrganizationFiltersFilter.TypeEnum.valueOf(type), pagination);
    }
}