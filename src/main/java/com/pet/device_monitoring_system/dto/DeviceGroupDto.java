package com.pet.device_monitoring_system.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

public record DeviceGroupDto(
        String name,
        String code,
        String description,
        String location) {
}
