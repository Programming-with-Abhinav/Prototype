package com.sih26006.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log", indexes = {
        @Index(name = "idx_audit_user", columnList = "user_id"),
        @Index(name = "idx_audit_created", columnList = "created_at"),
        @Index(name = "idx_audit_table", columnList = "table_name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_log_id")
    private Long auditLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String action;

    @Column(nullable = false, length = 100)
    private String tableName;

    @Column(nullable = false)
    private Integer recordId;

    @Column(columnDefinition = "LONGTEXT")
    private String oldValues;

    @Column(columnDefinition = "LONGTEXT")
    private String newValues;

    private String ipAddress;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
