package com.pet.device_monitoring_system.services.deviceGroupService;

import com.pet.device_monitoring_system.dto.DeviceGroupDto;

import java.util.List;
import java.util.UUID;

public interface DeviceGroupService {
    DeviceGroupDto createDeviceGroup(DeviceGroupDto deviceGroupDto);

    DeviceGroupDto findGroupById(UUID id);

    List<DeviceGroupDto> findAllGroups();

    DeviceGroupDto findGroupByCode(String code);

    DeviceGroupDto updateGroup(UUID id, DeviceGroupDto deviceGroupDto);

    DeviceGroupDto deleteGroup(UUID id);


}
