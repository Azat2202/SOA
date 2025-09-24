package ru.itmo.soa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Embedded
    private CoordinatesEmbeddable coordinates;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @NotNull
    @Min(1)
    @Column(name = "annual_turnover", nullable = false)
    private Long annualTurnover;

    @Size(max = 918)
    @Column(name = "full_name")
    private String fullName;

    @Min(1)
    @Column(name = "employees_count")
    private Integer employeesCount;

    public enum TypeEnum {
        PUBLIC,
        TRUST,
        OPEN_JOINT_STOCK_COMPANY
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeEnum type;

    @Embedded
    private AddressEmbeddable postalAddress;

    @PrePersist
    void prePersist() {
        if (creationDate == null) {
            creationDate = LocalDate.now();
        }
    }
} 