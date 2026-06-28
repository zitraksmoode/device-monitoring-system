package com.pet.device_monitoring_system.controllers;

import com.pet.device_monitoring_system.dto.DeviceDto;
import com.pet.device_monitoring_system.entity.StatusType;
import com.pet.device_monitoring_system.services.deviceService.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;

    DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public DeviceDto createDevice(@RequestBody DeviceDto deviceDto) {
        return deviceService.createDevice(deviceDto);
    }

    @GetMapping("/{id}")
    public DeviceDto getDeviceById(@PathVariable UUID id) {
        return deviceService.findDeviceById(id);
    }

    @GetMapping
    public List<DeviceDto> getAllDevice() {
        return deviceService.findAllDevices();
    }

    @GetMapping("/group/{id}")
    public List<DeviceDto> getAllDeviceByGroupId(@PathVariable UUID id) {
        return deviceService.findDevicesByGroupId(id);
    }

    @GetMapping("/status/{status}")
    public List<DeviceDto> getAllDeviceByStatus(@PathVariable StatusType status) {
        return deviceService.findDevicesByStatus(status);
    }

    @PutMapping("/{id}")
    public DeviceDto updateDevice(@RequestBody DeviceDto deviceDto, @PathVariable UUID id) {
        return deviceService.updateDevice(id, deviceDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable UUID id) {
        deviceService.deleteDevice(id);
    }

    @PatchMapping("/{id}/status")
    public DeviceDto updateDeviceStatus(@RequestBody StatusType status, @PathVariable UUID id) {
        return deviceService.changeDeviceStatus(id, status);
    }

}
