package com.pet.device_monitoring_system.repository;

import com.pet.device_monitoring_system.entity.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DeviceRepository extends CrudRepository<Device, UUID> {
}
