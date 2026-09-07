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
@Table(name = "freight_history", indexes = {
        @Index(name = "idx_freight_date_rate", columnList = "recorded_date, freight_rate_per_tonne"),
        @Index(name = "idx_route", columnList = "origin_id, destination_port_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreightHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "freight_history_id")
    private Integer freightHistoryId;

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
    private BigDecimal freightRatePerTonne;

    @Column(nullable = false)
    private LocalDate recordedDate;

    @Column(nullable = false)
    private Integer volatilityIndex = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
