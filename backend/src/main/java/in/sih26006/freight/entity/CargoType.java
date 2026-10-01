package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "cargo_types",
    indexes = {
        @Index(name = "idx_cargo_name", columnList = "cargo_name"),
        @Index(name = "idx_cargo_category", columnList = "cargo_category")
    }
)
public class CargoType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cargo_type_id")
    private Integer cargoTypeId;

    @Column(name = "cargo_name", nullable = false, unique = true, length = 100)
    private String cargoName;

    @Column(name = "cargo_category", length = 20)
    private String cargoCategory = "bulk";

    @Column(name = "is_hazardous")
    private Boolean isHazardous = false;

    @Column(name = "special_requirements", length = 255)
    private String specialRequirements;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public CargoType() {
    }

    public CargoType(String cargoName, String cargoCategory, boolean isHazardous) {
        this.cargoName = cargoName;
        this.cargoCategory = cargoCategory;
        this.isHazardous = isHazardous;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Integer getCargoTypeId() { return cargoTypeId; }
    public String getCargoName() { return cargoName; }
    public void setCargoName(String cargoName) { this.cargoName = cargoName; }
    public String getCargoCategory() { return cargoCategory; }
    public void setCargoCategory(String cargoCategory) { this.cargoCategory = cargoCategory; }
    public Boolean getIsHazardous() { return isHazardous; }
    public void setIsHazardous(Boolean isHazardous) { this.isHazardous = isHazardous; }
    public String getSpecialRequirements() { return specialRequirements; }
    public void setSpecialRequirements(String specialRequirements) { this.specialRequirements = specialRequirements; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
