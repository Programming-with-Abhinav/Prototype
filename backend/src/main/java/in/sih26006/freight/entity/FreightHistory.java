package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "freight_history",
    indexes = {
        @Index(name = "idx_fh_recorded_date", columnList = "recorded_date"),
        @Index(name = "idx_fh_route", columnList = "origin_id,destination_port_id"),
        @Index(name = "idx_fh_cargo_type", columnList = "cargo_type_id")
    },
    uniqueConstraints = @UniqueConstraint(
        name = "unique_route_date",
        columnNames = {"origin_id", "destination_port_id", "cargo_type_id", "vessel_type_id", "recorded_date"}
    )
)
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
    private Vessel vessel;

    @Column(name = "freight_rate_per_tonne", nullable = false, precision = 10, scale = 2)
    private BigDecimal freightRatePerTonne;

    @Column(name = "recorded_date", nullable = false)
    private LocalDate recordedDate;

    @Column(name = "volatility_index", precision = 5, scale = 2)
    private BigDecimal volatilityIndex;

    @Column(name = "supply_demand_index")
    private Integer supplyDemandIndex;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public FreightHistory() {
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Integer getFreightHistoryId() { return freightHistoryId; }
    public Origin getOrigin() { return origin; }
    public void setOrigin(Origin origin) { this.origin = origin; }
    public Port getDestinationPort() { return destinationPort; }
    public void setDestinationPort(Port destinationPort) { this.destinationPort = destinationPort; }
    public CargoType getCargoType() { return cargoType; }
    public void setCargoType(CargoType cargoType) { this.cargoType = cargoType; }
    public Vessel getVessel() { return vessel; }
    public void setVessel(Vessel vessel) { this.vessel = vessel; }
    public BigDecimal getFreightRatePerTonne() { return freightRatePerTonne; }
    public void setFreightRatePerTonne(BigDecimal freightRatePerTonne) { this.freightRatePerTonne = freightRatePerTonne; }
    public LocalDate getRecordedDate() { return recordedDate; }
    public void setRecordedDate(LocalDate recordedDate) { this.recordedDate = recordedDate; }
    public BigDecimal getVolatilityIndex() { return volatilityIndex; }
    public void setVolatilityIndex(BigDecimal volatilityIndex) { this.volatilityIndex = volatilityIndex; }
    public Integer getSupplyDemandIndex() { return supplyDemandIndex; }
    public void setSupplyDemandIndex(Integer supplyDemandIndex) { this.supplyDemandIndex = supplyDemandIndex; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
