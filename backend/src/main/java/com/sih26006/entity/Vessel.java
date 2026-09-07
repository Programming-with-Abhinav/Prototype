package com.sih26006.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "vessels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vessel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vessel_id")
    private Integer vesselId;

    @Column(unique = true, nullable = false)
    private String vesselType;

    @Column(nullable = false)
    private Integer cargoCapacityTonnes;

    @Column(nullable = false)
    private Double draftMeters;

    @Column(nullable = false)
    private Double loaLengthMeters;

    @Column(nullable = false)
    private Double beamWidthMeters;

    private Double dailyOperatingCostUSD;

    private String description;

    @Column(nullable = false)
    private Boolean isActive = true;
}
