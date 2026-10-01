package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "ports",
    indexes = {
        @Index(name = "idx_port_name", columnList = "port_name"),
        @Index(name = "idx_country", columnList = "country"),
        @Index(name = "idx_is_operational", columnList = "is_operational")
    }
)
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "port_id")
    private Integer portId;

    @Column(name = "port_name", nullable = false, unique = true, length = 100)
    private String portName;

    @Column(name = "country", length = 50)
    private String country = "India";

    @Column(name = "state_province", length = 50)
    private String stateProvince;

    @Column(name = "max_draft_meters", nullable = false, precision = 5, scale = 2)
    private BigDecimal maxDraftMeters;

    @Column(name = "max_loa_meters", nullable = false)
    private Integer maxLoaMeters;

    @Column(name = "max_beam_meters", nullable = false)
    private Integer maxBeamMeters;

    @Column(name = "annual_cargo_capacity_millions")
    private Integer annualCargoCapacityMillions;

    @Column(name = "current_congestion_percentage")
    private Integer currentCongestionPercentage = 0;

    @Column(name = "is_operational")
    private Boolean isOperational = true;

    // Stored for weather-service geo lookups; not part of canonical schema table definition
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Port() {
    }

    /**
     * Legacy constructor used by DemoDataInitializer.
     * congestionStatus string is converted to percentage (Low=10, Moderate=50, High=80).
     */
    public Port(String portName, double maxDraftMeters, double maxLoaMeters, double maxBeamMeters,
                String congestionStatus, double latitude, double longitude) {
        this.portName = portName;
        this.maxDraftMeters = BigDecimal.valueOf(maxDraftMeters);
        this.maxLoaMeters = (int) maxLoaMeters;
        this.maxBeamMeters = (int) maxBeamMeters;
        this.currentCongestionPercentage = parseCongestion(congestionStatus);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private static int parseCongestion(String status) {
        if (status == null) return 0;
        return switch (status.toLowerCase()) {
            case "high" -> 80;
            case "moderate" -> 50;
            default -> 10;
        };
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
    public Integer getPortId() { return portId; }
    public String getPortName() { return portName; }
    public void setPortName(String portName) { this.portName = portName; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getStateProvince() { return stateProvince; }
    public void setStateProvince(String stateProvince) { this.stateProvince = stateProvince; }
    public BigDecimal getMaxDraftMeters() { return maxDraftMeters; }
    public void setMaxDraftMeters(BigDecimal maxDraftMeters) { this.maxDraftMeters = maxDraftMeters; }
    public Integer getMaxLoaMeters() { return maxLoaMeters; }
    public void setMaxLoaMeters(Integer maxLoaMeters) { this.maxLoaMeters = maxLoaMeters; }
    public Integer getMaxBeamMeters() { return maxBeamMeters; }
    public void setMaxBeamMeters(Integer maxBeamMeters) { this.maxBeamMeters = maxBeamMeters; }
    public Integer getAnnualCargoCapacityMillions() { return annualCargoCapacityMillions; }
    public void setAnnualCargoCapacityMillions(Integer annualCargoCapacityMillions) { this.annualCargoCapacityMillions = annualCargoCapacityMillions; }
    public Integer getCurrentCongestionPercentage() { return currentCongestionPercentage; }
    public void setCurrentCongestionPercentage(Integer currentCongestionPercentage) { this.currentCongestionPercentage = currentCongestionPercentage; }
    public Boolean getIsOperational() { return isOperational; }
    public void setIsOperational(Boolean isOperational) { this.isOperational = isOperational; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Legacy-compatible getters used by DecisionService
    public String getName() { return portName; }
    public double getMaxDraft() { return maxDraftMeters != null ? maxDraftMeters.doubleValue() : 0; }
    public double getMaxLoa() { return maxLoaMeters != null ? maxLoaMeters : 0; }
    public double getMaxBeam() { return maxBeamMeters != null ? maxBeamMeters : 0; }
    public String getCongestionStatus() {
        if (currentCongestionPercentage == null || currentCongestionPercentage <= 20) return "Low";
        if (currentCongestionPercentage <= 60) return "Moderate";
        return "High";
    }
}
