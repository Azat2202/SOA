package ru.itmo.soa.services;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import ru.itmo.gen.model.Organization;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFilters;
import ru.itmo.gen.model.OrganizationFiltersSortInner;
import ru.itmo.soa.models.OrganizationEntity;
import ru.itmo.soa.repositories.OrganizationsRepository;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class OrganizationFilterService {

    @Inject
    private ModelMapper modelMapper;

    @Inject
    private OrganizationsRepository organizationsRepository;

    public Response organizationsFilterPost(OrganizationFilters organizationFilters) {
        if (organizationFilters.getFilter() == null) {
            OrganizationFiltersFilter organizationFiltersFilter = new OrganizationFiltersFilter();
            organizationFiltersFilter.setType(OrganizationFiltersFilter.TypeEnum.PUBLIC);
            organizationFilters.setFilter(organizationFiltersFilter);
        }
        int page = organizationFilters.getPagination() != null && organizationFilters.getPagination().getPage() != null
                ? organizationFilters.getPagination().getPage() : 0;
        int size = organizationFilters.getPagination() != null && organizationFilters.getPagination().getSize() != null
                ? organizationFilters.getPagination().getSize() : 20;

        List<SortOrder> sortOrders = buildSort(organizationFilters);

        OrganizationsRepository.PageResult<OrganizationEntity> result =
                organizationsRepository.findAllByFilters(organizationFilters, page, size, sortOrders);

        List<Organization> organizations = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, Organization.class))
                .toList();

        OrganizationArray response = new OrganizationArray()
                .organizations(organizations)
                .totalCount((int) result.getTotalElements())
                .page(page)
                .size(size);

        return Response.ok(response).build();
    }

    private List<SortOrder> buildSort(OrganizationFilters filters) {
        List<SortOrder> orders = new ArrayList<>();
        if (filters.getSort() == null || filters.getSort().isEmpty()) {
            orders.add(new SortOrder("id", true));
            return orders;
        }
        for (OrganizationFiltersSortInner s : filters.getSort()) {
            String property = mapSortFieldToProperty(s.getField());
            if (property == null) {
                continue;
            }
            boolean ascending = s.getDirection() != OrganizationFiltersSortInner.DirectionEnum.DESC;
            orders.add(new SortOrder(property, ascending));
        }
        return orders.isEmpty() ? List.of(new SortOrder("id", true)) : orders;
    }

    private String mapSortFieldToProperty(OrganizationFiltersSortInner.FieldEnum field) {
        if (field == null) return null;
        return switch (field) {
            case ID -> "id";
            case NAME -> "name";
            case CREATION_DATE -> "creationDate";
            case ANNUAL_TURNOVER -> "annualTurnover";
            case FULL_NAME -> "fullName";
            case EMPLOYEES_COUNT -> "employeesCount";
            case TYPE -> "type";
            case ADDRESS_STREET -> "postalAddress.street";
            case ADDRESS_TOWN_X -> "postalAddress.town.x";
            case ADDRESS_TOWN_Y -> "postalAddress.town.y";
            case ADDRESS_TOWN_Z -> "postalAddress.town.z";
            case ADDRESS_TOWN_NAME -> "postalAddress.town.name";
            case COORDINATES_X -> "coordinates.x";
            case COORDINATES_Y -> "coordinates.y";
            case LOCATION_NAME -> "postalAddress.town.name";
            default -> null;
        };
    }

    public static class SortOrder {
        private final String property;
        private final boolean ascending;

        public SortOrder(String property, boolean ascending) {
            this.property = property;
            this.ascending = ascending;
        }

        public String getProperty() {
            return property;
        }

        public boolean isAscending() {
            return ascending;
        }
    }
}
