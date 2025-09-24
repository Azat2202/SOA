package ru.itmo.soa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class LocationEmbeddable {

    @NotNull
    @Column(name = "location_x", nullable = false)
    private Float x;

    @NotNull
    @Column(name = "location_y", nullable = false)
    private Double y;

    @NotNull
    @Column(name = "location_z", nullable = false)
    private Long z;

    @Column(name = "location_name")
    private String name;
} 