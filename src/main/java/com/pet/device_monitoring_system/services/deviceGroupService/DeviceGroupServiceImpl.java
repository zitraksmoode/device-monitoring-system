package com.pet.device_monitoring_system.services.deviceGroupService;

import com.pet.device_monitoring_system.dto.DeviceGroupDto;
import com.pet.device_monitoring_system.entity.DeviceGroup;
import com.pet.device_monitoring_system.repository.DeviceGroupRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
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
        deviceGroupRepository.save(deviceGroup);
        return new DeviceGroupDto(deviceGroupDto.name(),
                deviceGroupDto.code(),
                deviceGroupDto.description(),
                deviceGroupDto.location());
    }

    @Override
    public DeviceGroupDto findGroupById(UUID id) {
        DeviceGroup deviceGroup = deviceGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Illegal argument"));
        return new DeviceGroupDto(deviceGroup.getName(),
                deviceGroup.getCode(),
                deviceGroup.getDescription(),
                deviceGroup.getLocation());
    }

    @Override
    public List<DeviceGroupDto> findAllGroups() {
        Iterable<DeviceGroup> deviceGroups = deviceGroupRepository.findAll();
        List<DeviceGroupDto> deviceGroupDtoList = new ArrayList<>();
        for (DeviceGroup group : deviceGroups) {
            deviceGroupDtoList.add(new DeviceGroupDto(group.getName(), group.getCode(), group.getDescription(), group.getLocation()));
        }
        return deviceGroupDtoList;
    }

    @Override
    public DeviceGroupDto findGroupByCode(String code) {
        DeviceGroup deviceGroup = deviceGroupRepository.findByCode(code);
        return new DeviceGroupDto(deviceGroup.getName(),
                deviceGroup.getCode(),
                deviceGroup.getDescription(),
                deviceGroup.getLocation());
    }

    @Override
    public DeviceGroupDto updateGroup(UUID id, DeviceGroupDto deviceGroupDto) {
        DeviceGroup deviceGroup = deviceGroupRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Test"));
        deviceGroup.setName(deviceGroupDto.name());
        deviceGroup.setCode(deviceGroupDto.code());
        deviceGroup.setDescription(deviceGroupDto.description());
        deviceGroup.setLocation(deviceGroupDto.location());
        deviceGroupRepository.save(deviceGroup);
        return new DeviceGroupDto(deviceGroup.getName(),
                deviceGroup.getCode(),
                deviceGroup.getDescription(),
                deviceGroup.getLocation());
    }

    @Override
    public void deleteGroup(UUID id) {
        deviceGroupRepository.deleteById(id);
    }
}
