package com.sih26006.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cargo_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargoType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cargo_type_id")
    private Integer cargoTypeId;

    @Column(unique = true, nullable = false)
    private String cargoName;

    private String description;

    private Integer averageDensityKgM3;

    @Column(nullable = false)
    private Boolean isActive = true;
}
