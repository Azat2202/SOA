package ru.itmo.soa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CoordinatesEmbeddable {

    @NotNull
    @Min(-365)
    @Column(name = "coordinates_x", nullable = false)
    private Integer x;

    @NotNull
    @Column(name = "coordinates_y", nullable = false)
    private Double y;
} 