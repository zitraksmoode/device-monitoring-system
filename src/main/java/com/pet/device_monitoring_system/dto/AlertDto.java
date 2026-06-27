package com.pet.device_monitoring_system.dto;

import com.pet.device_monitoring_system.entity.AlertSeverityType;
import com.pet.device_monitoring_system.entity.AlertStatusType;

public record AlertDto(
        String alertType,
        String message,
        AlertSeverityType severity,
        AlertStatusType status) {
}
