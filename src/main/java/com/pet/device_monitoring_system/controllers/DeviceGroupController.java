package com.pet.device_monitoring_system.controllers;

import com.pet.device_monitoring_system.dto.DeviceGroupDto;
import com.pet.device_monitoring_system.entity.DeviceGroup;
import com.pet.device_monitoring_system.services.deviceGroupService.DeviceGroupService;
import com.pet.device_monitoring_system.services.deviceGroupService.DeviceGroupServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/device-group")
public class DeviceGroupController {
    private final DeviceGroupService deviceGroupService;

    public DeviceGroupController(DeviceGroupService deviceGroupService) {
        this.deviceGroupService = deviceGroupService;
    }

    @PostMapping
    public DeviceGroupDto createDeviceGroup(@RequestBody DeviceGroupDto deviceGroupDto) {
        return deviceGroupService.createDeviceGroup(deviceGroupDto);
    }

    @GetMapping("/{id}")
    public DeviceGroupDto getDeviceGroupById(@PathVariable UUID id) {
        return deviceGroupService.findGroupById(id);
    }

    @GetMapping("/all")
    public List<DeviceGroupDto> getAllDeviceGroup() {
        return deviceGroupService.findAllGroups();
    }

    @GetMapping("/code/{code}")
    public DeviceGroupDto getDeviceGroupByCode(@PathVariable String code) {
        return deviceGroupService.findGroupByCode(code);
    }

    @PutMapping("/{id}")
    public DeviceGroupDto updateDeviceGroup(@PathVariable UUID id, @RequestBody DeviceGroupDto deviceGroupDto) {
       return deviceGroupService.updateGroup(id, deviceGroupDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDeviceGroup(@PathVariable UUID id) {
        deviceGroupService.deleteGroup(id);
    }

}
