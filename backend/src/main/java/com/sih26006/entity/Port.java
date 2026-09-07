package com.sih26006.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "port_id")
    private Integer portId;

    @Column(unique = true, nullable = false)
    private String portName;

    private String stateProvince;

    private String country;

    @Column(nullable = false)
    private Double maxDraftMeters;

    @Column(nullable = false)
    private Double maxLoaMeters;

    @Column(nullable = false)
    private Double maxBeamMeters;

    @Column(nullable = false)
    private Integer currentCongestionPercentage = 0;

    @Column(nullable = false)
    private Double maxCargoCapacityTonnes;

    private String description;

    @Column(nullable = false)
    private Boolean isOperational = true;
}
