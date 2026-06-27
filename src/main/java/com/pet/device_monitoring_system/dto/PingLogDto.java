package com.pet.device_monitoring_system.dto;

import com.pet.device_monitoring_system.entity.Device;
import com.pet.device_monitoring_system.entity.PingKindType;
import com.pet.device_monitoring_system.entity.PingResultType;


import java.time.Instant;

public record PingLogDto(
        Device device,
        String errorMessage,
        Integer responseTimeMs,
        Instant createdAt,
        PingKindType pingType,
        PingResultType result) {
}
