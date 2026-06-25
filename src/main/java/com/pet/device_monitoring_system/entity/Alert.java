package com.pet.device_monitoring_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Size(max = 255)
    @NotNull
    @Column(name = "alert_type", nullable = false)
    private String alertType;

    @NotNull
    @Column(name = "message", nullable = false, length = Integer.MAX_VALUE)
    private String message;
    @Column(name = "closed_at")
    private Instant closedAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "opened_at")
    private Instant openedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "closed_by")
    private User closedBy;

    @Column(name = "severity", columnDefinition = "alert_severity_type not null")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AlertSeverityType severity;


    @ColumnDefault("'OPEN'")
    @Column(name = "status", columnDefinition = "alert_status_type not null")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AlertStatusType status;
}