package com.pet.device_monitoring_system.entity;

import jakarta.persistence.*;
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
@Table(name = "ping_logs")
public class PingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "error_message", length = Integer.MAX_VALUE)
    private String errorMessage;

    @Column(name = "response_time_ms")
    private Integer responseTimeMs;
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;


    @Column(name = "ping_type", columnDefinition = "ping_kind_type not null")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private PingKindType pingType;


    @Column(name = "result", columnDefinition = "ping_result_type not null")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private PingResultType result;
}