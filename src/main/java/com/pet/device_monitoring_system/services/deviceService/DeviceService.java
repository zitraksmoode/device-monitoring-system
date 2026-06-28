package com.pet.device_monitoring_system.services.deviceService;

import com.pet.device_monitoring_system.dto.DeviceDto;
import com.pet.device_monitoring_system.entity.StatusType;

import java.util.List;
import java.util.UUID;

public interface DeviceService {
    DeviceDto createDevice(DeviceDto deviceDto);
    DeviceDto findDeviceById(UUID id);
    List<DeviceDto> findAllDevices();
    List<DeviceDto> findDevicesByGroupId(UUID groupId);
    List<DeviceDto> findDevicesByStatus(StatusType status);
    DeviceDto updateDevice(UUID id, DeviceDto deviceDto);
    void deleteDevice(UUID id);
    DeviceDto changeDeviceStatus(UUID id, StatusType status);
}

