package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "vessel_compatibility",
    indexes = @Index(name = "idx_vc_route", columnList = "origin_id,destination_port_id"),
    uniqueConstraints = @UniqueConstraint(
        name = "unique_compatibility",
        columnNames = {"origin_id", "destination_port_id", "vessel_type_id", "cargo_quantity_tonnes"}
    )
)
public class VesselCompatibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compatibility_id")
    private Integer compatibilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id", nullable = false)
    private Origin origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_port_id", nullable = false)
    private Port destinationPort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_type_id", nullable = false)
    private Vessel vessel;

    @Column(name = "cargo_quantity_tonnes", nullable = false)
    private Integer cargoQuantityTonnes;

    @Column(name = "draft_compatible")
    private Boolean draftCompatible;

    @Column(name = "loa_compatible")
    private Boolean loaCompatible;

    @Column(name = "beam_compatible")
    private Boolean beamCompatible;

    @Column(name = "capacity_sufficient")
    private Boolean capacitySufficient;

    @Column(name = "overall_compatibility", length = 50)
    private String overallCompatibility;

    @Column(name = "compatibility_score")
    private Integer compatibilityScore;

    @Column(name = "notes", length = 255)
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public VesselCompatibility() {
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

    public Integer getCompatibilityId() { return compatibilityId; }
    public Origin getOrigin() { return origin; }
    public void setOrigin(Origin origin) { this.origin = origin; }
    public Port getDestinationPort() { return destinationPort; }
    public void setDestinationPort(Port destinationPort) { this.destinationPort = destinationPort; }
    public Vessel getVessel() { return vessel; }
    public void setVessel(Vessel vessel) { this.vessel = vessel; }
    public Integer getCargoQuantityTonnes() { return cargoQuantityTonnes; }
    public void setCargoQuantityTonnes(Integer cargoQuantityTonnes) { this.cargoQuantityTonnes = cargoQuantityTonnes; }
    public Boolean getDraftCompatible() { return draftCompatible; }
    public void setDraftCompatible(Boolean draftCompatible) { this.draftCompatible = draftCompatible; }
    public Boolean getLoaCompatible() { return loaCompatible; }
    public void setLoaCompatible(Boolean loaCompatible) { this.loaCompatible = loaCompatible; }
    public Boolean getBeamCompatible() { return beamCompatible; }
    public void setBeamCompatible(Boolean beamCompatible) { this.beamCompatible = beamCompatible; }
    public Boolean getCapacitySufficient() { return capacitySufficient; }
    public void setCapacitySufficient(Boolean capacitySufficient) { this.capacitySufficient = capacitySufficient; }
    public String getOverallCompatibility() { return overallCompatibility; }
    public void setOverallCompatibility(String overallCompatibility) { this.overallCompatibility = overallCompatibility; }
    public Integer getCompatibilityScore() { return compatibilityScore; }
    public void setCompatibilityScore(Integer compatibilityScore) { this.compatibilityScore = compatibilityScore; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
