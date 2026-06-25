package com.pet.device_monitoring_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "ip_address")
    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "group_id")
    private DeviceGroup group;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "last_ping_at")
    private Instant lastPingAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;
    @OneToMany
    @JoinColumn(name = "device_id")
    private Set<Alert> alerts = new LinkedHashSet<>();

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;
    @OneToMany
    @JoinColumn(name = "device_id")
    private Set<PingLog> pingLogs = new LinkedHashSet<>();


    @ColumnDefault("'ATM'")
    @Column(name = "device", columnDefinition = "device_type")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)

    private DeviceType device;


    @ColumnDefault("'UNKNOWN'")
    @Column(name = "status", columnDefinition = "status_type")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private StatusType status;
}