package com.pet.device_monitoring_system.repository;

import com.pet.device_monitoring_system.entity.Device;
import com.pet.device_monitoring_system.entity.DeviceGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DeviceGroupRepository extends CrudRepository<DeviceGroup, UUID> {
    DeviceGroup findByCode(String code);
}
