package com.pet.device_monitoring_system.repository;

import com.pet.device_monitoring_system.entity.Device;
import com.pet.device_monitoring_system.entity.StatusType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends CrudRepository<Device, UUID> {
    List<Device> findByGroup_Id(UUID groupId);

    Iterable<Device> findDevicesByStatus(StatusType status);
}
