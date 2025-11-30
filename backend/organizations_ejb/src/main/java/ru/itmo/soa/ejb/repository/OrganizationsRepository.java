package ru.itmo.soa.ejb.repository;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import ru.itmo.gen.model.OrganizationFilters;
import ru.itmo.gen.model.OrganizationFiltersFilter;
import ru.itmo.soa.ejb.model.OrganizationEntity;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrganizationsRepository {

    @PersistenceContext(unitName = "OrganizationsPU")
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public OrganizationEntity save(OrganizationEntity entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    public OrganizationEntity findById(Long id) {
        return entityManager.find(OrganizationEntity.class, id);
    }

    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(OrganizationEntity entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            OrganizationEntity managed = entityManager.merge(entity);
            entityManager.remove(managed);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteById(Long id) {
        OrganizationEntity entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }

    public OrganizationEntity findByFullName(String fullName) {
        TypedQuery<OrganizationEntity> query = entityManager.createQuery(
                "SELECT o FROM OrganizationEntity o WHERE o.fullName = :fullName", OrganizationEntity.class);
        query.setParameter("fullName", fullName);
        List<OrganizationEntity> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public Integer countByEmployeesCount(Integer employeesCount) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(o) FROM OrganizationEntity o WHERE o.employeesCount = :count", Long.class);
        query.setParameter("count", employeesCount);
        return query.getSingleResult().intValue();
    }

    public Long countByAnnualTurnoverBefore(Long annualTurnover) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(o) FROM OrganizationEntity o WHERE o.annualTurnover < :turnover", Long.class);
        query.setParameter("turnover", annualTurnover);
        return query.getSingleResult();
    }

    public PageResult<OrganizationEntity> findAllByFilters(
            OrganizationFilters filters, int page, int size, List<SortOrder> sortOrders) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<OrganizationEntity> cq = cb.createQuery(OrganizationEntity.class);
        Root<OrganizationEntity> root = cq.from(OrganizationEntity.class);

        List<Predicate> predicates = buildPredicates(cb, root, filters);
        cq.where(predicates.toArray(new Predicate[0]));

        List<Order> orders = new ArrayList<>();
        for (SortOrder sortOrder : sortOrders) {
            Path<?> path = resolvePath(root, sortOrder.getProperty());
            if (sortOrder.isAscending()) {
                orders.add(cb.asc(path));
            } else {
                orders.add(cb.desc(path));
            }
        }
        if (!orders.isEmpty()) {
            cq.orderBy(orders);
        }

        TypedQuery<OrganizationEntity> query = entityManager.createQuery(cq);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<OrganizationEntity> content = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<OrganizationEntity> countRoot = countQuery.from(OrganizationEntity.class);
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, filters);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageResult<>(content, total, page, size);
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
        if (f.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + f.getName().toLowerCase() + "%"));
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
                predicates.add(cb.like(cb.lower(root.get("postalAddress").get("street")),
                        "%" + f.getPostalAddress().getStreet().toLowerCase() + "%"));
            }
            if (f.getPostalAddress().getTown() != null) {
                if (f.getPostalAddress().getTown().getX() != null) {
                    predicates.add(cb.equal(root.get("postalAddress").get("town").get("x"),
                            f.getPostalAddress().getTown().getX().floatValue()));
                }
                if (f.getPostalAddress().getTown().getY() != null) {
                    predicates.add(cb.equal(root.get("postalAddress").get("town").get("y"),
                            f.getPostalAddress().getTown().getY()));
                }
                if (f.getPostalAddress().getTown().getZ() != null) {
                    predicates.add(cb.equal(root.get("postalAddress").get("town").get("z"),
                            f.getPostalAddress().getTown().getZ()));
                }
                if (f.getPostalAddress().getTown().getName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("postalAddress").get("town").get("name")),
                            "%" + f.getPostalAddress().getTown().getName().toLowerCase() + "%"));
                }
            }
        }
        return predicates;
    }

    private Path<?> resolvePath(Root<OrganizationEntity> root, String propertyPath) {
        String[] parts = propertyPath.split("\\.");
        Path<?> path = root;
        for (String part : parts) {
            path = path.get(part);
        }
        return path;
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

    public static class PageResult<T> {
        private final List<T> content;
        private final long totalElements;
        private final int page;
        private final int size;

        public PageResult(List<T> content, long totalElements, int page, int size) {
            this.content = content;
            this.totalElements = totalElements;
            this.page = page;
            this.size = size;
        }

        public List<T> getContent() {
            return content;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public int getPage() {
            return page;
        }

        public int getSize() {
            return size;
        }
    }
}

