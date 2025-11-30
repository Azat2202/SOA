package ru.itmo.soa.ejb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddressEmbeddable {

    @NotBlank
    @Column(name = "address_street", nullable = false)
    private String street;

    @NotNull
    @Embedded
    private LocationEmbeddable town;
}

