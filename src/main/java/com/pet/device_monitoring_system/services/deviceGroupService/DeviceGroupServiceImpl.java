package com.pet.device_monitoring_system.services.deviceGroupService;

import com.pet.device_monitoring_system.dto.DeviceGroupDto;
import com.pet.device_monitoring_system.entity.DeviceGroup;
import com.pet.device_monitoring_system.repository.DeviceGroupRepository;

import java.util.List;
import java.util.UUID;

public class DeviceGroupServiceImpl implements DeviceGroupService {
    private final DeviceGroupRepository deviceGroupRepository;

    public DeviceGroupServiceImpl(DeviceGroupRepository deviceGroupRepository) {
        this.deviceGroupRepository = deviceGroupRepository;
    }

    @Override
    public DeviceGroupDto createDeviceGroup(DeviceGroupDto deviceGroupDto) {
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setCode(deviceGroupDto.code());
        deviceGroup.setName(deviceGroupDto.name());
        deviceGroup.setDescription(deviceGroupDto.description());
        deviceGroup.setLocation(deviceGroupDto.location());
        return new DeviceGroupDto(deviceGroupDto.name(),
                deviceGroupDto.code(),
                deviceGroupDto.description(),
                deviceGroupDto.location());
    }

    @Override
    public DeviceGroupDto findGroupById(UUID id) {

        return null;
    }

    @Override
    public List<DeviceGroupDto> findAllGroups() {
        return List.of();
    }

    @Override
    public DeviceGroupDto findGroupByCode(String code) {
        return null;
    }

    @Override
    public DeviceGroupDto updateGroup(UUID id, DeviceGroupDto deviceGroupDto) {
        return null;
    }

    @Override
    public DeviceGroupDto deleteGroup(UUID id) {
        return null;
    }
}
