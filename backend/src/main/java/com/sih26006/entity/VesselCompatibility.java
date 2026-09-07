package com.sih26006.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vessel_compatibility")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VesselCompatibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compatibility_id")
    private Integer compatibilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_type_id", nullable = false)
    private Vessel vesselType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id", nullable = false)
    private Origin origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_port_id", nullable = false)
    private Port destinationPort;

    @Column(nullable = false)
    private Integer cargoQuantityTonnes;

    @Column(nullable = false)
    private Integer compatibilityScore;

    @Column(nullable = false)
    private Boolean draftCompatible;

    @Column(nullable = false)
    private Boolean loaCompatible;

    @Column(nullable = false)
    private Boolean beamCompatible;

    @Column(nullable = false)
    private Boolean capacityCompatible;

    @Column(nullable = false)
    private Boolean overallCompatibility;

    private String compatibilityNotes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
