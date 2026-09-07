package com.sih26006.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_assessments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "risk_assessment_id")
    private Integer riskAssessmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id", nullable = false)
    private Origin origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_port_id", nullable = false)
    private Port destinationPort;

    @Column(nullable = false)
    private LocalDate assessmentDate;

    @Column(nullable = false)
    private Integer freightVolatilityScore;

    @Column(nullable = false)
    private Integer portCongestionScore;

    @Column(nullable = false)
    private Integer demandUncertaintyScore;

    @Column(nullable = false)
    private Integer vesselCompatibilityScore;

    @Column(nullable = false)
    private Integer overallRiskScore;

    @Column(nullable = false, length = 20)
    private String riskLevel;

    private String riskFactorsJson;

    private String mitigationStrategies;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
