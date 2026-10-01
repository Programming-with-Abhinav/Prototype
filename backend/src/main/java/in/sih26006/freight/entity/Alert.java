package in.sih26006.freight.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "alerts",
    indexes = {
        @Index(name = "idx_alert_user_id", columnList = "user_id"),
        @Index(name = "idx_alert_priority_level", columnList = "priority_level"),
        @Index(name = "idx_alert_is_read", columnList = "is_read"),
        @Index(name = "idx_alert_created_at", columnList = "created_at"),
        @Index(name = "idx_alert_type", columnList = "alert_type")
    }
)
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Integer alertId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "alert_type", nullable = false, length = 50)
    private String alertType;

    @Column(name = "priority_level", length = 20)
    private String priorityLevel = "medium";

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_forecast_id")
    private Forecast relatedForecast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_port_id")
    private Port relatedPort;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    public Alert() {
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Integer getAlertId() { return alertId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }
    public String getPriorityLevel() { return priorityLevel; }
    public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Forecast getRelatedForecast() { return relatedForecast; }
    public void setRelatedForecast(Forecast relatedForecast) { this.relatedForecast = relatedForecast; }
    public Port getRelatedPort() { return relatedPort; }
    public void setRelatedPort(Port relatedPort) { this.relatedPort = relatedPort; }
    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getReadAt() { return readAt; }
    public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }
}
