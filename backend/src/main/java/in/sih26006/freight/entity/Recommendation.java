package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "recommendations",
    indexes = {
        @Index(name = "idx_rec_user_id", columnList = "user_id"),
        @Index(name = "idx_rec_created_at", columnList = "created_at"),
        @Index(name = "idx_rec_confidence_level", columnList = "confidence_level")
    }
)
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Integer recommendationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forecast_id")
    private Forecast forecast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_assessment_id")
    private RiskAssessment riskAssessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id", nullable = false)
    private Origin origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_port_id", nullable = false)
    private Port destinationPort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_vessel_type_id", nullable = false)
    private Vessel recommendedVessel;

    @Column(name = "recommended_strategy", length = 255)
    private String recommendedStrategy;

    @Column(name = "strategy_rationale", columnDefinition = "TEXT")
    private String strategyRationale;

    @Column(name = "expected_freight_cost", precision = 15, scale = 2)
    private BigDecimal expectedFreightCost;

    @Column(name = "risk_level", length = 50)
    private String riskLevel;

    @Column(name = "market_entry_timing", length = 100)
    private String marketEntryTiming;

    @Column(name = "contract_duration_recommendation")
    private Integer contractDurationRecommendation;

    @Column(name = "confidence_level")
    private Integer confidenceLevel;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Recommendation() {
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Integer getRecommendationId() { return recommendationId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Forecast getForecast() { return forecast; }
    public void setForecast(Forecast forecast) { this.forecast = forecast; }
    public RiskAssessment getRiskAssessment() { return riskAssessment; }
    public void setRiskAssessment(RiskAssessment riskAssessment) { this.riskAssessment = riskAssessment; }
    public Origin getOrigin() { return origin; }
    public void setOrigin(Origin origin) { this.origin = origin; }
    public Port getDestinationPort() { return destinationPort; }
    public void setDestinationPort(Port destinationPort) { this.destinationPort = destinationPort; }
    public Vessel getRecommendedVessel() { return recommendedVessel; }
    public void setRecommendedVessel(Vessel recommendedVessel) { this.recommendedVessel = recommendedVessel; }
    public String getRecommendedStrategy() { return recommendedStrategy; }
    public void setRecommendedStrategy(String recommendedStrategy) { this.recommendedStrategy = recommendedStrategy; }
    public String getStrategyRationale() { return strategyRationale; }
    public void setStrategyRationale(String strategyRationale) { this.strategyRationale = strategyRationale; }
    public BigDecimal getExpectedFreightCost() { return expectedFreightCost; }
    public void setExpectedFreightCost(BigDecimal expectedFreightCost) { this.expectedFreightCost = expectedFreightCost; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public String getMarketEntryTiming() { return marketEntryTiming; }
    public void setMarketEntryTiming(String marketEntryTiming) { this.marketEntryTiming = marketEntryTiming; }
    public Integer getContractDurationRecommendation() { return contractDurationRecommendation; }
    public void setContractDurationRecommendation(Integer contractDurationRecommendation) { this.contractDurationRecommendation = contractDurationRecommendation; }
    public Integer getConfidenceLevel() { return confidenceLevel; }
    public void setConfidenceLevel(Integer confidenceLevel) { this.confidenceLevel = confidenceLevel; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
