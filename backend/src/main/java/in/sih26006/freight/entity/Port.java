package in.sih26006.freight.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ports")
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private double maxDraft;
    private double maxLoa;
    private double maxBeam;
    private String congestionStatus;
    private double latitude;
    private double longitude;

    public Port() {
    }

    public Port(String n, double d, double l, double b, String c, double lat, double lon) {
        name = n;
        maxDraft = d;
        maxLoa = l;
        maxBeam = b;
        congestionStatus = c;
        latitude = lat;
        longitude = lon;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxDraft() {
        return maxDraft;
    }

    public double getMaxLoa() {
        return maxLoa;
    }

    public double getMaxBeam() {
        return maxBeam;
    }

    public String getCongestionStatus() {
        return congestionStatus;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
