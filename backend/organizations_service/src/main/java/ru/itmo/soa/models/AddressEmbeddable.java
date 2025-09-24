package ru.itmo.soa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEmbeddable {

    @NotBlank
    @Column(name = "address_street", nullable = false)
    private String street;

    @NotNull
    @Embedded
    private LocationEmbeddable town;
} 