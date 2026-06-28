package com.pet.device_monitoring_system.services.deviceService;

import com.pet.device_monitoring_system.dto.DeviceDto;
import com.pet.device_monitoring_system.entity.Device;
import com.pet.device_monitoring_system.entity.StatusType;
import com.pet.device_monitoring_system.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DeviceDto createDevice(DeviceDto deviceDto) {
        Device device = new Device();
        device.setName(deviceDto.name());
        device.setIpAddress(deviceDto.ipAddress());
        device.setGroup(deviceDto.group());
        device.setIsActive(true);
        device.setDevice(deviceDto.deviceType());
        device.setStatus(StatusType.UNKNOWN);
        deviceRepository.save(device);
        return new DeviceDto(device.getName(),
                device.getIpAddress(),
                device.getGroup(),
                device.getIsActive(),
                device.getDevice(),
                device.getStatus());
    }

    @Override
    public DeviceDto findDeviceById(UUID id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Something"));
        return new DeviceDto(device.getName(),
                device.getIpAddress(),
                device.getGroup(),
                device.getIsActive(),
                device.getDevice(),
                device.getStatus());
    }

    @Override
    public List<DeviceDto> findAllDevices() {
        Iterable<Device> devices = deviceRepository.findAll();
        List<DeviceDto> dtoList = new ArrayList<>();
        for (Device device : devices) {
            dtoList.add(new DeviceDto(device.getName(),
                    device.getIpAddress(),
                    device.getGroup(),
                    device.getIsActive(),
                    device.getDevice(),
                    device.getStatus()));
        }
        return dtoList;
    }

    @Override
    public List<DeviceDto> findDevicesByGroupId(UUID groupId) {
        Iterable<Device> devices = deviceRepository.findByGroup_Id(groupId);
        List<DeviceDto> dtoList = new ArrayList<>();
        for (Device device : devices) {
            dtoList.add(new DeviceDto(device.getName(),
                    device.getIpAddress(),
                    device.getGroup(),
                    device.getIsActive(),
                    device.getDevice(),
                    device.getStatus()));
        }
        return dtoList;
    }

    @Override
    public List<DeviceDto> findDevicesByStatus(StatusType status) {
        Iterable<Device> devices = deviceRepository.findDevicesByStatus(status);
        List<DeviceDto> dtoList = new ArrayList<>();
        for (Device device : devices) {
            dtoList.add(new DeviceDto(device.getName(),
                    device.getIpAddress(),
                    device.getGroup(),
                    device.getIsActive(),
                    device.getDevice(),
                    device.getStatus()));
        }
        return dtoList;
    }

    @Override
    public DeviceDto updateDevice(UUID id, DeviceDto deviceDto) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Something"));
        device.setStatus(deviceDto.status());
        device.setName(deviceDto.name());
        device.setIpAddress(deviceDto.ipAddress());
        device.setGroup(deviceDto.group());
        device.setDevice(deviceDto.deviceType());
        deviceRepository.save(device);

        return new DeviceDto(device.getName(),
                device.getIpAddress(),
                device.getGroup(),
                device.getIsActive(),
                device.getDevice(),
                device.getStatus());
    }

    @Override
    public void deleteDevice(UUID id) {
        if(deviceRepository.findById(id).isPresent()) {
            deviceRepository.deleteById(id);
        }
    }

    @Override
    public DeviceDto changeDeviceStatus(UUID id, StatusType status) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Something"));
        device.setStatus(status);
        deviceRepository.save(device);
        return new DeviceDto(device.getName(),
                device.getIpAddress(),
                device.getGroup(),
                device.getIsActive(),
                device.getDevice(),
                device.getStatus());
    }
}
