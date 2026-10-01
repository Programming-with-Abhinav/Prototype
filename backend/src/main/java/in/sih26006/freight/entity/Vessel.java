package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "vessels",
    indexes = @Index(name = "idx_vessel_type", columnList = "vessel_type")
)
public class Vessel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vessel_id")
    private Integer vesselId;

    @Column(name = "vessel_type", nullable = false, unique = true, length = 50)
    private String vesselType;

    @Column(name = "cargo_capacity_tonnes", nullable = false)
    private Integer cargoCapacityTonnes;

    @Column(name = "draft_meters", nullable = false, precision = 5, scale = 2)
    private BigDecimal draftMeters;

    @Column(name = "loa_length_meters", nullable = false)
    private Integer loaLengthMeters;

    @Column(name = "beam_width_meters", nullable = false)
    private Integer beamWidthMeters;

    @Column(name = "age_years")
    private Integer ageYears;

    @Column(name = "fuel_consumption_per_day", precision = 8, scale = 2)
    private BigDecimal fuelConsumptionPerDay;

    @Column(name = "average_day_rate_usd")
    private Integer averageDayRateUsd;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Vessel() {
    }

    public Vessel(String vesselType, int cargoCapacityTonnes, double draftMeters, int loaLengthMeters, int beamWidthMeters) {
        this.vesselType = vesselType;
        this.cargoCapacityTonnes = cargoCapacityTonnes;
        this.draftMeters = BigDecimal.valueOf(draftMeters);
        this.loaLengthMeters = loaLengthMeters;
        this.beamWidthMeters = beamWidthMeters;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (updatedAt == null) updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // --- Getters & Setters ---
    public Integer getVesselId() { return vesselId; }
    public String getVesselType() { return vesselType; }
    public void setVesselType(String vesselType) { this.vesselType = vesselType; }
    public Integer getCargoCapacityTonnes() { return cargoCapacityTonnes; }
    public void setCargoCapacityTonnes(Integer cargoCapacityTonnes) { this.cargoCapacityTonnes = cargoCapacityTonnes; }
    public BigDecimal getDraftMeters() { return draftMeters; }
    public void setDraftMeters(BigDecimal draftMeters) { this.draftMeters = draftMeters; }
    public Integer getLoaLengthMeters() { return loaLengthMeters; }
    public void setLoaLengthMeters(Integer loaLengthMeters) { this.loaLengthMeters = loaLengthMeters; }
    public Integer getBeamWidthMeters() { return beamWidthMeters; }
    public void setBeamWidthMeters(Integer beamWidthMeters) { this.beamWidthMeters = beamWidthMeters; }
    public Integer getAgeYears() { return ageYears; }
    public void setAgeYears(Integer ageYears) { this.ageYears = ageYears; }
    public BigDecimal getFuelConsumptionPerDay() { return fuelConsumptionPerDay; }
    public void setFuelConsumptionPerDay(BigDecimal fuelConsumptionPerDay) { this.fuelConsumptionPerDay = fuelConsumptionPerDay; }
    public Integer getAverageDayRateUsd() { return averageDayRateUsd; }
    public void setAverageDayRateUsd(Integer averageDayRateUsd) { this.averageDayRateUsd = averageDayRateUsd; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Legacy-compatible getters used by DecisionService
    public double getDraft() { return draftMeters != null ? draftMeters.doubleValue() : 0; }
    public double getLoa() { return loaLengthMeters != null ? loaLengthMeters : 0; }
    public double getBeam() { return beamWidthMeters != null ? beamWidthMeters : 0; }
    public double getCapacityTonnes() { return cargoCapacityTonnes != null ? cargoCapacityTonnes : 0; }
}
