package ru.itmo.soa.services;

import io.getunleash.Unleash;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.itmo.gen.model.*;
import ru.itmo.soa.models.OrganizationEntity;
import ru.itmo.soa.repositories.OrganizationsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationFilterService {
    private final ModelMapper modelMapper;
    private final OrganizationsRepository organizationsRepository;
    private final Unleash unleash;

    @PersistenceContext
    private EntityManager entityManager;

    public ResponseEntity<OrganizationArray> organizationsFilterPost(OrganizationFilters organizationFilters) {
        SessionFactoryImplementor sfi =
                entityManager.getEntityManagerFactory()
                        .unwrap(SessionFactoryImplementor.class);
        Dialect dialect = sfi.getJdbcServices().getDialect();
        System.out.println("dialect = " + dialect);

        boolean skipPublic = unleash.isEnabled("skip-public-companies");

        if (skipPublic) {
            if (organizationFilters.getFilter() == null) {
                OrganizationFiltersFilter organizationFiltersFilter = new OrganizationFiltersFilter();
                organizationFiltersFilter.setType(OrganizationFiltersFilter.TypeEnum.PUBLIC);
                organizationFilters.setFilter(organizationFiltersFilter);
            } else {
                organizationFilters.getFilter().setType(OrganizationFiltersFilter.TypeEnum.PUBLIC);
            }
        }


        int page = organizationFilters.getPagination() != null && organizationFilters.getPagination().getPage() != null
                ? organizationFilters.getPagination().getPage() : 0;
        int size = organizationFilters.getPagination() != null && organizationFilters.getPagination().getSize() != null
                ? organizationFilters.getPagination().getSize() : 20;

        Sort sort = buildSort(organizationFilters);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<OrganizationEntity> result = organizationsRepository.findAllByFilters(organizationFilters, pageable);

        List<Organization> organizations = result
                .getContent()
                .stream()
                .map(organizationEntity -> modelMapper.map(organizationEntity, Organization.class))
                .toList();

        OrganizationArray response = new OrganizationArray()
                .organizations(organizations)
                .totalCount((int) result.getTotalElements())
                .page(result.getNumber())
                .size(result.getSize());

        return ResponseEntity.ok(response);
    }

    private Sort buildSort(OrganizationFilters filters) {
        if (filters.getSort() == null || filters.getSort().isEmpty()) {
            return Sort.by(Sort.Order.asc("id"));
        }
        List<Sort.Order> orders = new ArrayList<>();
        for (OrganizationFiltersSortInner s : filters.getSort()) {
            String property = mapSortFieldToProperty(s.getField());
            if (property == null) {
                continue;
            }
            Sort.Order order = s.getDirection() == OrganizationFiltersSortInner.DirectionEnum.DESC
                    ? Sort.Order.desc(property)
                    : Sort.Order.asc(property);
            orders.add(order);
        }
        return orders.isEmpty() ? Sort.by(Sort.Order.asc("id")) : Sort.by(orders);
    }

    private String mapSortFieldToProperty(OrganizationFiltersSortInner.FieldEnum field) {
        if (field == null) return null;
        switch (field) {
            case ID:
                return "id";
            case NAME:
                return "name";
            case CREATION_DATE:
                return "creationDate";
            case ANNUAL_TURNOVER:
                return "annualTurnover";
            case FULL_NAME:
                return "fullName";
            case EMPLOYEES_COUNT:
                return "employeesCount";
            case TYPE:
                return "type";
            case ADDRESS_STREET:
                return "postalAddress.street";
            case ADDRESS_TOWN_X:
                return "postalAddress.town.x";
            case ADDRESS_TOWN_Y:
                return "postalAddress.town.y";
            case ADDRESS_TOWN_Z:
                return "postalAddress.town.z";
            case ADDRESS_TOWN_NAME:
                return "postalAddress.town.name";
            case COORDINATES_X:
                return "coordinates.x";
            case COORDINATES_Y:
                return "coordinates.y";
            case LOCATION_NAME:
                return "postalAddress.town.name";
            default:
                return null;
        }
    }
}
