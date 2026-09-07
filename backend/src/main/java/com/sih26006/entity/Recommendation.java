package com.sih26006.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Integer recommendationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id", nullable = false)
    private Origin origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_port_id", nullable = false)
    private Port destinationPort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_vessel_type_id", nullable = false)
    private Vessel recommendedVesselType;

    @Column(nullable = false, length = 500)
    private String recommendedStrategy;

    @Column(nullable = false)
    private BigDecimal expectedFreightCost;

    @Column(nullable = false, length = 20)
    private String riskLevel;

    @Column(nullable = false)
    private Integer confidenceLevel;

    private String rationale;

    private String idleTimeManagement;

    private String notes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
