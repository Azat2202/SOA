package ru.itmo.soa.ejb.service;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import ru.itmo.gen.model.Organization;
import ru.itmo.gen.model.OrganizationArray;
import ru.itmo.gen.model.OrganizationFilters;
import ru.itmo.gen.model.OrganizationFiltersSortInner;
import ru.itmo.soa.api.OrganizationFilterServiceLocal;
import ru.itmo.soa.api.OrganizationFilterServiceRemote;
import ru.itmo.soa.ejb.model.OrganizationEntity;
import ru.itmo.soa.ejb.repository.OrganizationsRepository;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrganizationFilterServiceBean implements OrganizationFilterServiceLocal, OrganizationFilterServiceRemote {

    @Inject
    private ModelMapper modelMapper;

    @EJB
    private OrganizationsRepository organizationsRepository;

    @Override
    public OrganizationArray filterOrganizations(OrganizationFilters organizationFilters) {
        int page = organizationFilters.getPagination() != null && organizationFilters.getPagination().getPage() != null
                ? organizationFilters.getPagination().getPage() : 0;
        int size = organizationFilters.getPagination() != null && organizationFilters.getPagination().getSize() != null
                ? organizationFilters.getPagination().getSize() : 20;

        List<OrganizationsRepository.SortOrder> sortOrders = buildSort(organizationFilters);

        OrganizationsRepository.PageResult<OrganizationEntity> result =
                organizationsRepository.findAllByFilters(organizationFilters, page, size, sortOrders);

        List<Organization> organizations = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, Organization.class))
                .toList();

        return new OrganizationArray()
                .organizations(organizations)
                .totalCount((int) result.getTotalElements())
                .page(page)
                .size(size);
    }

    private List<OrganizationsRepository.SortOrder> buildSort(OrganizationFilters filters) {
        List<OrganizationsRepository.SortOrder> orders = new ArrayList<>();
        if (filters.getSort() == null || filters.getSort().isEmpty()) {
            orders.add(new OrganizationsRepository.SortOrder("id", true));
            return orders;
        }
        for (OrganizationFiltersSortInner s : filters.getSort()) {
            String property = mapSortFieldToProperty(s.getField());
            if (property == null) {
                continue;
            }
            boolean ascending = s.getDirection() != OrganizationFiltersSortInner.DirectionEnum.DESC;
            orders.add(new OrganizationsRepository.SortOrder(property, ascending));
        }
        return orders.isEmpty() ? List.of(new OrganizationsRepository.SortOrder("id", true)) : orders;
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
}
