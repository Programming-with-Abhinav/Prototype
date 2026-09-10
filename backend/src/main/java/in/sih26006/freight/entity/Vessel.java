package in.sih26006.freight.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vessels")
public class Vessel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String vesselType;
    private double capacityTonnes;
    private double draft;
    private double loa;
    private double beam;

    public Vessel() {
    }

    public Vessel(String t, double c, double d, double l, double b) {
        vesselType = t;
        capacityTonnes = c;
        draft = d;
        loa = l;
        beam = b;
    }

    public Long getId() {
        return id;
    }

    public String getVesselType() {
        return vesselType;
    }

    public double getCapacityTonnes() {
        return capacityTonnes;
    }

    public double getDraft() {
        return draft;
    }

    public double getLoa() {
        return loa;
    }

    public double getBeam() {
        return beam;
    }
}
