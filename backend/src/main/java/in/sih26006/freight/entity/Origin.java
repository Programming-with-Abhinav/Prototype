package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "origins",
    indexes = {
        @Index(name = "idx_origin_name", columnList = "origin_name"),
        @Index(name = "idx_origin_country", columnList = "country"),
        @Index(name = "idx_origin_is_active", columnList = "is_active")
    }
)
public class Origin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "origin_id")
    private Integer originId;

    @Column(name = "origin_name", nullable = false, unique = true, length = 100)
    private String originName;

    @Column(name = "location_type", length = 20)
    private String locationType = "country";

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Origin() {
    }

    public Origin(String originName, String locationType, String country) {
        this.originName = originName;
        this.locationType = locationType;
        this.country = country;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Integer getOriginId() { return originId; }
    public String getOriginName() { return originName; }
    public void setOriginName(String originName) { this.originName = originName; }
    public String getLocationType() { return locationType; }
    public void setLocationType(String locationType) { this.locationType = locationType; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
