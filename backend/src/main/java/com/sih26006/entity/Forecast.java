package com.sih26006.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "forecasts", indexes = {
        @Index(name = "idx_user_forecast_date", columnList = "user_id, forecast_date"),
        @Index(name = "idx_forecast_created", columnList = "created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forecast_id")
    private Integer forecastId;

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
    @JoinColumn(name = "cargo_type_id", nullable = false)
    private CargoType cargoType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_type_id", nullable = false)
    private Vessel vesselType;

    @Column(nullable = false)
    private Integer cargoQuantityTonnes;

    private Integer contractDurationMonths;

    @Column(nullable = false)
    private Integer forecastPeriodDays;

    @Column(nullable = false)
    private BigDecimal currentFreightRate;

    @Column(nullable = false)
    private BigDecimal predictedFreightRate;

    @Column(length = 50)
    private String forecastTrend;

    private BigDecimal trendPercentage;

    private Integer confidenceLevel;

    @Column(nullable = false)
    private LocalDate forecastDate;

    @Column(nullable = false)
    private LocalDate forecastedDate;

    @Column(length = 100)
    private String algorithmUsed;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
