package com.pet.device_monitoring_system.services.deviceService;

import com.pet.device_monitoring_system.dto.DeviceDto;
import com.pet.device_monitoring_system.entity.Device;
import com.pet.device_monitoring_system.entity.DeviceGroup;
import com.pet.device_monitoring_system.entity.DeviceType;
import com.pet.device_monitoring_system.entity.StatusType;
import com.pet.device_monitoring_system.repository.DeviceRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Test
    void createDevice_whenDtoIsValid_shouldCreateDeviceWithDefaultStatusAndActiveTrue() {
        // given
        DeviceGroup group = new DeviceGroup();

        DeviceDto request = new DeviceDto(
                "ATM-001",
                "192.168.1.10",
                group,
                false,
                DeviceType.ATM,
                StatusType.BAD
        );

        // when
        DeviceDto result = deviceService.createDevice(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("ATM-001");
        assertThat(result.ipAddress()).isEqualTo("192.168.1.10");
        assertThat(result.group()).isSameAs(group);
        assertThat(result.isActive()).isTrue();
        assertThat(result.deviceType()).isEqualTo(DeviceType.ATM);
        assertThat(result.status()).isEqualTo(StatusType.UNKNOWN);

        ArgumentCaptor<Device> deviceCaptor = ArgumentCaptor.forClass(Device.class);
        verify(deviceRepository).save(deviceCaptor.capture());

        Device savedDevice = deviceCaptor.getValue();

        assertThat(savedDevice.getName()).isEqualTo("ATM-001");
        assertThat(savedDevice.getIpAddress()).isEqualTo("192.168.1.10");
        assertThat(savedDevice.getGroup()).isSameAs(group);
        assertThat(savedDevice.getIsActive()).isTrue();
        assertThat(savedDevice.getDevice()).isEqualTo(DeviceType.ATM);
        assertThat(savedDevice.getStatus()).isEqualTo(StatusType.UNKNOWN);
    }

    @Test
    void findDeviceById_whenDeviceExists_shouldReturnDeviceDto() {
        // given
        UUID id = UUID.randomUUID();
        Device device = createDeviceEntity("ATM-001", "192.168.1.10", StatusType.GOOD);

        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

        // when
        DeviceDto result = deviceService.findDeviceById(id);

        // then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("ATM-001");
        assertThat(result.ipAddress()).isEqualTo("192.168.1.10");
        assertThat(result.status()).isEqualTo(StatusType.GOOD);

        verify(deviceRepository).findById(id);
    }

    @Test
    void findDeviceById_whenDeviceDoesNotExist_shouldThrowException() {
        // given
        UUID id = UUID.randomUUID();

        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        assertThatThrownBy(() -> deviceService.findDeviceById(id))
                .isInstanceOf(IllegalArgumentException.class);

        verify(deviceRepository).findById(id);
    }

    @Test
    void findAllDevices_whenDevicesExist_shouldReturnDeviceDtoList() {
        // given
        Device device1 = createDeviceEntity("ATM-001", "192.168.1.10", StatusType.GOOD);
        Device device2 = createDeviceEntity("SERVER-001", "192.168.1.20", StatusType.BAD);

        when(deviceRepository.findAll()).thenReturn(List.of(device1, device2));

        // when
        List<DeviceDto> result = deviceService.findAllDevices();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        assertThat(result.get(0).name()).isEqualTo("ATM-001");
        assertThat(result.get(0).status()).isEqualTo(StatusType.GOOD);

        assertThat(result.get(1).name()).isEqualTo("SERVER-001");
        assertThat(result.get(1).status()).isEqualTo(StatusType.BAD);

        verify(deviceRepository).findAll();
    }

    @Test
    void findDevicesByGroupId_whenDevicesExist_shouldReturnDeviceDtoList() {
        // given
        UUID groupId = UUID.randomUUID();

        Device device1 = createDeviceEntity("ATM-001", "192.168.1.10", StatusType.GOOD);
        Device device2 = createDeviceEntity("ATM-002", "192.168.1.11", StatusType.UNKNOWN);

        when(deviceRepository.findByGroup_Id(groupId)).thenReturn(List.of(device1, device2));

        // when
        List<DeviceDto> result = deviceService.findDevicesByGroupId(groupId);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("ATM-001");
        assertThat(result.get(1).name()).isEqualTo("ATM-002");

        verify(deviceRepository).findByGroup_Id(groupId);
    }

    @Test
    void findDevicesByStatus_whenDevicesExist_shouldReturnDeviceDtoList() {
        // given
        Device device1 = createDeviceEntity("ATM-001", "192.168.1.10", StatusType.BAD);
        Device device2 = createDeviceEntity("ATM-002", "192.168.1.11", StatusType.BAD);

        when(deviceRepository.findDevicesByStatus(StatusType.BAD))
                .thenReturn(List.of(device1, device2));

        // when
        List<DeviceDto> result = deviceService.findDevicesByStatus(StatusType.BAD);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).status()).isEqualTo(StatusType.BAD);
        assertThat(result.get(1).status()).isEqualTo(StatusType.BAD);

        verify(deviceRepository).findDevicesByStatus(StatusType.BAD);
    }

    @Test
    void updateDevice_whenDeviceExists_shouldUpdateDevice() {
        // given
        UUID id = UUID.randomUUID();

        Device existingDevice = createDeviceEntity("OLD-NAME", "192.168.1.1", StatusType.UNKNOWN);
        DeviceGroup newGroup = new DeviceGroup();

        DeviceDto updateRequest = new DeviceDto(
                "NEW-NAME",
                "10.0.0.5",
                newGroup,
                true,
                DeviceType.SERVER,
                StatusType.GOOD
        );

        when(deviceRepository.findById(id)).thenReturn(Optional.of(existingDevice));

        // when
        DeviceDto result = deviceService.updateDevice(id, updateRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("NEW-NAME");
        assertThat(result.ipAddress()).isEqualTo("10.0.0.5");
        assertThat(result.group()).isSameAs(newGroup);
        assertThat(result.deviceType()).isEqualTo(DeviceType.SERVER);
        assertThat(result.status()).isEqualTo(StatusType.GOOD);

        verify(deviceRepository).findById(id);
        verify(deviceRepository).save(existingDevice);
    }

    @Test
    void updateDevice_whenDeviceDoesNotExist_shouldThrowException() {
        // given
        UUID id = UUID.randomUUID();

        DeviceDto updateRequest = new DeviceDto(
                "NEW-NAME",
                "10.0.0.5",
                new DeviceGroup(),
                true,
                DeviceType.SERVER,
                StatusType.GOOD
        );

        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        assertThatThrownBy(() -> deviceService.updateDevice(id, updateRequest))
                .isInstanceOf(IllegalArgumentException.class);

        verify(deviceRepository).findById(id);
        verify(deviceRepository, never()).save(any(Device.class));
    }

    @Test
    void deleteDevice_whenDeviceExists_shouldDeleteDevice() {
        // given
        UUID id = UUID.randomUUID();
        Device device = createDeviceEntity("ATM-001", "192.168.1.10", StatusType.GOOD);

        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

        // when
        deviceService.deleteDevice(id);

        // then
        verify(deviceRepository).findById(id);
        verify(deviceRepository).deleteById(id);
    }

    @Test
    void deleteDevice_whenDeviceDoesNotExist_shouldNotDeleteDevice() {
        // given
        UUID id = UUID.randomUUID();

        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        // when
        deviceService.deleteDevice(id);

        // then
        verify(deviceRepository).findById(id);
        verify(deviceRepository, never()).deleteById(id);
    }

    @Test
    void changeDeviceStatus_whenDeviceExists_shouldChangeStatus() {
        // given
        UUID id = UUID.randomUUID();
        Device device = createDeviceEntity("ATM-001", "192.168.1.10", StatusType.UNKNOWN);

        when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

        // when
        DeviceDto result = deviceService.changeDeviceStatus(id, StatusType.BAD);

        // then
        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo(StatusType.BAD);
        assertThat(device.getStatus()).isEqualTo(StatusType.BAD);

        verify(deviceRepository).findById(id);
        verify(deviceRepository).save(device);
    }

    @Test
    void changeDeviceStatus_whenDeviceDoesNotExist_shouldThrowException() {
        // given
        UUID id = UUID.randomUUID();

        when(deviceRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        assertThatThrownBy(() -> deviceService.changeDeviceStatus(id, StatusType.BAD))
                .isInstanceOf(IllegalArgumentException.class);

        verify(deviceRepository).findById(id);
        verify(deviceRepository, never()).save(any(Device.class));
    }

    private Device createDeviceEntity(String name, String ipAddress, StatusType status) {
        Device device = new Device();
        device.setName(name);
        device.setIpAddress(ipAddress);
        device.setGroup(new DeviceGroup());
        device.setIsActive(true);
        device.setDevice(DeviceType.ATM);
        device.setStatus(status);
        return device;
    }
}