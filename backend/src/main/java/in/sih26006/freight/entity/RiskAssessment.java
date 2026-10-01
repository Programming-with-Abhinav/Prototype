package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "risk_assessments",
    indexes = {
        @Index(name = "idx_ra_user_id", columnList = "user_id"),
        @Index(name = "idx_ra_risk_level", columnList = "risk_level"),
        @Index(name = "idx_ra_assessment_date", columnList = "assessment_date")
    }
)
public class RiskAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "risk_assessment_id")
    private Integer riskAssessmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forecast_id")
    private Forecast forecast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id", nullable = false)
    private Origin origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_port_id", nullable = false)
    private Port destinationPort;

    @Column(name = "freight_volatility_score")
    private Integer freightVolatilityScore;

    @Column(name = "port_congestion_score")
    private Integer portCongestionScore;

    @Column(name = "demand_uncertainty_score")
    private Integer demandUncertaintyScore;

    @Column(name = "vessel_compatibility_score")
    private Integer vesselCompatibilityScore;

    @Column(name = "overall_risk_score")
    private Integer overallRiskScore;

    @Column(name = "risk_level", length = 50)
    private String riskLevel;

    @Column(name = "risk_factors_json", columnDefinition = "JSON")
    private String riskFactorsJson;

    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;

    @Column(name = "assessment_date")
    private LocalDateTime assessmentDate;

    public RiskAssessment() {
    }

    @PrePersist
    protected void onCreate() {
        if (assessmentDate == null) assessmentDate = LocalDateTime.now();
    }

    public Integer getRiskAssessmentId() { return riskAssessmentId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Forecast getForecast() { return forecast; }
    public void setForecast(Forecast forecast) { this.forecast = forecast; }
    public Origin getOrigin() { return origin; }
    public void setOrigin(Origin origin) { this.origin = origin; }
    public Port getDestinationPort() { return destinationPort; }
    public void setDestinationPort(Port destinationPort) { this.destinationPort = destinationPort; }
    public Integer getFreightVolatilityScore() { return freightVolatilityScore; }
    public void setFreightVolatilityScore(Integer freightVolatilityScore) { this.freightVolatilityScore = freightVolatilityScore; }
    public Integer getPortCongestionScore() { return portCongestionScore; }
    public void setPortCongestionScore(Integer portCongestionScore) { this.portCongestionScore = portCongestionScore; }
    public Integer getDemandUncertaintyScore() { return demandUncertaintyScore; }
    public void setDemandUncertaintyScore(Integer demandUncertaintyScore) { this.demandUncertaintyScore = demandUncertaintyScore; }
    public Integer getVesselCompatibilityScore() { return vesselCompatibilityScore; }
    public void setVesselCompatibilityScore(Integer vesselCompatibilityScore) { this.vesselCompatibilityScore = vesselCompatibilityScore; }
    public Integer getOverallRiskScore() { return overallRiskScore; }
    public void setOverallRiskScore(Integer overallRiskScore) { this.overallRiskScore = overallRiskScore; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public String getRiskFactorsJson() { return riskFactorsJson; }
    public void setRiskFactorsJson(String riskFactorsJson) { this.riskFactorsJson = riskFactorsJson; }
    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
    public LocalDateTime getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDateTime assessmentDate) { this.assessmentDate = assessmentDate; }
}
