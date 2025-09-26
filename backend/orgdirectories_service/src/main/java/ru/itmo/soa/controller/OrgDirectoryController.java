package ru.itmo.soa.controller;

import org.springframework.http.ResponseEntity;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFiltersFilter;
import ru.itmo.gen.model.Pagination;
import ru.itmo.soa.service.OrgDirectoryService;

import javax.inject.Inject;
import javax.persistence.EnumType;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/orgdirectory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrgDirectoryController {
    @Inject
    OrgDirectoryService orgDirectoryService;

    @POST
    @Path("//filter/turnover/{min-annual-turnover}/{max-annual-turnover}")
    public ResponseEntity<OrganizationArray> getOrganizationsInTurnoverRange(
            @PathParam("min-annual-turnover") Integer min,
            @PathParam("max-annual-turnover") Integer max,
            Pagination pagination) {
        return orgDirectoryService.getOrganizationsInRange(min, max, pagination);
        }


    @POST
    @Path("/filter/type/{type}")
    public ResponseEntity<OrganizationArray> getOrganizationsByType(
            @PathParam("type") String type,
            Pagination pagination
    ) {
        return orgDirectoryService.getOrganizationsByType(OrganizationFiltersFilter.TypeEnum.valueOf(type), pagination);
    }

}