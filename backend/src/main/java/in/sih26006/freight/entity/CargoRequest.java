package in.sih26006.freight.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cargo_requests", indexes = @Index(name = "idx_cargo_created", columnList = "createdAt"))
public class CargoRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String origin;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private String cargoType;
    private double quantityTonnes;
    private String preferredVessel;
    private int contractMonths;
    private java.time.Instant createdAt = java.time.Instant.now();

    public CargoRequest() {
    }

    public CargoRequest(String o, String d, String c, double q, String v, int m) {
        origin = o;
        destination = d;
        cargoType = c;
        quantityTonnes = q;
        preferredVessel = v;
        contractMonths = m;
    }

    public Long getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getCargoType() {
        return cargoType;
    }

    public double getQuantityTonnes() {
        return quantityTonnes;
    }

    public String getPreferredVessel() {
        return preferredVessel;
    }

    public int getContractMonths() {
        return contractMonths;
    }

    public java.time.Instant getCreatedAt() {
        return createdAt;
    }
}
