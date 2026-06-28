package com.pet.device_monitoring_system.dto;

import com.pet.device_monitoring_system.entity.DeviceGroup;
import com.pet.device_monitoring_system.entity.DeviceType;
import com.pet.device_monitoring_system.entity.StatusType;

public record DeviceDto(
        String name,
        String ipAddress,
        DeviceGroup group,
        Boolean isActive,
        DeviceType deviceType,
        StatusType status) {
}
