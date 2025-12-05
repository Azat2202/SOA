package ru.itmo.soa.ejb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

