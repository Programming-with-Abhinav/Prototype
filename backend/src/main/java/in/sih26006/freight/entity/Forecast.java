package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "forecasts",
    indexes = {
        @Index(name = "idx_forecast_user_id", columnList = "user_id"),
        @Index(name = "idx_forecast_date", columnList = "forecast_date"),
        @Index(name = "idx_forecast_route", columnList = "origin_id,destination_port_id")
    }
)
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forecast_id")
    private Integer forecastId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id", nullable = false)
    private Origin origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_port_id", nullable = false)
    private Port destinationPort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_type_id", nullable = false)
    private CargoType cargoType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_type_id", nullable = false)
    private Vessel vessel;

    @Column(name = "cargo_quantity_tonnes", nullable = false)
    private Integer cargoQuantityTonnes;

    @Column(name = "contract_duration_months")
    private Integer contractDurationMonths;

    @Column(name = "forecast_period_days", nullable = false)
    private Integer forecastPeriodDays;

    @Column(name = "current_freight_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentFreightRate;

    @Column(name = "predicted_freight_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal predictedFreightRate;

    @Column(name = "forecast_trend", length = 50)
    private String forecastTrend;

    @Column(name = "trend_percentage", precision = 6, scale = 2)
    private BigDecimal trendPercentage;

    @Column(name = "confidence_level")
    private Integer confidenceLevel;

    @Column(name = "forecast_date", nullable = false)
    private LocalDate forecastDate;

    @Column(name = "forecasted_date", nullable = false)
    private LocalDate forecastedDate;

    @Column(name = "algorithm_used", length = 100)
    private String algorithmUsed;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Forecast() {
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Integer getForecastId() { return forecastId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Origin getOrigin() { return origin; }
    public void setOrigin(Origin origin) { this.origin = origin; }
    public Port getDestinationPort() { return destinationPort; }
    public void setDestinationPort(Port destinationPort) { this.destinationPort = destinationPort; }
    public CargoType getCargoType() { return cargoType; }
    public void setCargoType(CargoType cargoType) { this.cargoType = cargoType; }
    public Vessel getVessel() { return vessel; }
    public void setVessel(Vessel vessel) { this.vessel = vessel; }
    public Integer getCargoQuantityTonnes() { return cargoQuantityTonnes; }
    public void setCargoQuantityTonnes(Integer cargoQuantityTonnes) { this.cargoQuantityTonnes = cargoQuantityTonnes; }
    public Integer getContractDurationMonths() { return contractDurationMonths; }
    public void setContractDurationMonths(Integer contractDurationMonths) { this.contractDurationMonths = contractDurationMonths; }
    public Integer getForecastPeriodDays() { return forecastPeriodDays; }
    public void setForecastPeriodDays(Integer forecastPeriodDays) { this.forecastPeriodDays = forecastPeriodDays; }
    public BigDecimal getCurrentFreightRate() { return currentFreightRate; }
    public void setCurrentFreightRate(BigDecimal currentFreightRate) { this.currentFreightRate = currentFreightRate; }
    public BigDecimal getPredictedFreightRate() { return predictedFreightRate; }
    public void setPredictedFreightRate(BigDecimal predictedFreightRate) { this.predictedFreightRate = predictedFreightRate; }
    public String getForecastTrend() { return forecastTrend; }
    public void setForecastTrend(String forecastTrend) { this.forecastTrend = forecastTrend; }
    public BigDecimal getTrendPercentage() { return trendPercentage; }
    public void setTrendPercentage(BigDecimal trendPercentage) { this.trendPercentage = trendPercentage; }
    public Integer getConfidenceLevel() { return confidenceLevel; }
    public void setConfidenceLevel(Integer confidenceLevel) { this.confidenceLevel = confidenceLevel; }
    public LocalDate getForecastDate() { return forecastDate; }
    public void setForecastDate(LocalDate forecastDate) { this.forecastDate = forecastDate; }
    public LocalDate getForecastedDate() { return forecastedDate; }
    public void setForecastedDate(LocalDate forecastedDate) { this.forecastedDate = forecastedDate; }
    public String getAlgorithmUsed() { return algorithmUsed; }
    public void setAlgorithmUsed(String algorithmUsed) { this.algorithmUsed = algorithmUsed; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
