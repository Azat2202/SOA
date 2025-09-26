package ru.itmo.soa.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.itmo.gen.model.OrganizationFilters;
import ru.itmo.gen.model.OrganizationFiltersFilter;
import ru.itmo.soa.models.OrganizationEntity;
import ru.itmo.soa.repositories.OrganizationsRepositoryCustom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizationsRepositoryImpl implements OrganizationsRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<OrganizationEntity> findAllByFilters(OrganizationFilters filters, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<OrganizationEntity> cq = cb.createQuery(OrganizationEntity.class);
        Root<OrganizationEntity> root = cq.from(OrganizationEntity.class);

        List<Predicate> predicates = buildPredicates(cb, root, filters);
        cq.where(predicates.toArray(new Predicate[0]));

        pageable.getSort().forEach(order -> {
            if (order.isAscending()) {
                cq.orderBy(cb.asc(resolvePath(root, order.getProperty())));
            } else {
                cq.orderBy(cb.desc(resolvePath(root, order.getProperty())));
            }
        });

        TypedQuery<OrganizationEntity> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<OrganizationEntity> content = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<OrganizationEntity> countRoot = countQuery.from(OrganizationEntity.class);
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, filters);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<OrganizationEntity> root, OrganizationFilters filters) {
        List<Predicate> predicates = new ArrayList<>();
        OrganizationFiltersFilter f = filters != null ? filters.getFilter() : null;
        if (f == null) {
            return predicates;
        }

        if (f.getOrganizationId() != null) {
            predicates.add(cb.equal(root.get("id"), f.getOrganizationId()));
        }
        if (f.getName() != null && f.getName().isPresent()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + f.getName().get().toLowerCase() + "%"));
        }
        if (f.getCoordinates() != null) {
            if (f.getCoordinates().getX() != null) {
                predicates.add(cb.equal(root.get("coordinates").get("x"), f.getCoordinates().getX()));
            }
            if (f.getCoordinates().getY() != null) {
                predicates.add(cb.equal(root.get("coordinates").get("y"), f.getCoordinates().getY()));
            }
        }
        if (f.getCreationDate() != null) {
            if (f.getCreationDate().getMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), f.getCreationDate().getMin()));
            }
            if (f.getCreationDate().getMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), f.getCreationDate().getMax()));
            }
        }
        if (f.getAnnualTurnover() != null) {
            if (f.getAnnualTurnover().getMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("annualTurnover"), f.getAnnualTurnover().getMin()));
            }
            if (f.getAnnualTurnover().getMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("annualTurnover"), f.getAnnualTurnover().getMax()));
            }
        }
        if (f.getFullName() != null) {
            predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + f.getFullName().toLowerCase() + "%"));
        }
        if (f.getEmployeesCount() != null) {
            predicates.add(cb.equal(root.get("employeesCount"), f.getEmployeesCount().intValue()));
        }
        if (f.getType() != null) {
            predicates.add(cb.equal(root.get("type"), OrganizationEntity.TypeEnum.valueOf(f.getType().getValue())));
        }
        if (f.getPostalAddress() != null) {
            if (f.getPostalAddress().getStreet() != null) {
                predicates.add(cb.like(cb.lower(root.get("postalAddress").get("street")), "%" + f.getPostalAddress().getStreet().toLowerCase() + "%"));
            }
            if (f.getPostalAddress().getTown() != null) {
                if (f.getPostalAddress().getTown().getX() != null) {
                    predicates.add(cb.equal(root.get("postalAddress").get("town").get("x"), f.getPostalAddress().getTown().getX().floatValue()));
                }
                if (f.getPostalAddress().getTown().getY() != null) {
                    predicates.add(cb.equal(root.get("postalAddress").get("town").get("y"), f.getPostalAddress().getTown().getY()));
                }
                if (f.getPostalAddress().getTown().getZ() != null) {
                    predicates.add(cb.equal(root.get("postalAddress").get("town").get("z"), f.getPostalAddress().getTown().getZ()));
                }
                if (f.getPostalAddress().getTown().getName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("postalAddress").get("town").get("name")), "%" + f.getPostalAddress().getTown().getName().toLowerCase() + "%"));
                }
            }
        }
        return predicates;
    }

    private jakarta.persistence.criteria.Path<?> resolvePath(Root<OrganizationEntity> root, String propertyPath) {
        String[] parts = propertyPath.split("\\.");
        jakarta.persistence.criteria.Path<?> path = root;
        for (String part : parts) {
            path = path.get(part);
        }
        return path;
    }
} 