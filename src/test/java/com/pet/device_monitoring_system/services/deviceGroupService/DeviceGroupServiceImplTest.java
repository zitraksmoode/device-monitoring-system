package com.pet.device_monitoring_system.services.deviceGroupService;

import com.pet.device_monitoring_system.dto.DeviceGroupDto;
import com.pet.device_monitoring_system.entity.DeviceGroup;
import com.pet.device_monitoring_system.repository.DeviceGroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DeviceGroupServiceImplTest {
    @InjectMocks
    private DeviceGroupServiceImpl deviceGroupService;

    @Mock
    private DeviceGroupRepository deviceGroupRepository;

    @Test
    void createDeviceGroup() {
        DeviceGroupDto deviceGroupDto = new DeviceGroupDto("TestName", "123", "123", "123");
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setName(deviceGroupDto.name());
        deviceGroup.setCode(deviceGroupDto.code());
        deviceGroup.setLocation(deviceGroupDto.location());
        deviceGroup.setDescription(deviceGroupDto.description());

        when(deviceGroupRepository.save(any(DeviceGroup.class))).thenReturn(deviceGroup);

        DeviceGroupDto deviceGroupDto1 = deviceGroupService.createDeviceGroup(deviceGroupDto);

        assertThat(deviceGroupDto1).isNotNull();
        assertThat(deviceGroupDto1.name()).isEqualTo("TestName");
        assertThat(deviceGroupDto1.code()).isEqualTo("123");
        assertThat(deviceGroupDto1.description()).isEqualTo("123");
        assertThat(deviceGroupDto1.location()).isEqualTo("123");

    }

    @Test
    void findGroupById() {
        DeviceGroupDto deviceGroupDto = new DeviceGroupDto("TestName", "123", "123", "123");
        UUID id = UUID.randomUUID();
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setId(id);
        deviceGroup.setName(deviceGroupDto.name());
        deviceGroup.setCode(deviceGroupDto.code());
        deviceGroup.setLocation(deviceGroupDto.location());
        deviceGroup.setDescription(deviceGroupDto.description());

        when(deviceGroupRepository.findById(any(UUID.class))).thenReturn(Optional.of(deviceGroup));

        DeviceGroupDto deviceGroupDto1 = deviceGroupService.findGroupById(id);


        assertThat(deviceGroupDto1).isNotNull();
        assertThat(deviceGroupDto1.name()).isEqualTo("TestName");
        assertThat(deviceGroupDto1.code()).isEqualTo("123");
        assertThat(deviceGroupDto1.description()).isEqualTo("123");
        assertThat(deviceGroupDto1.location()).isEqualTo("123");


    }

    @Test
    void findAllGroups() {
        DeviceGroup first = new DeviceGroup();
        first.setName("First");
        DeviceGroup second = new DeviceGroup();
        second.setName("Second");
        List<DeviceGroup> deviceGroups = List.of(first, second);

        when(deviceGroupRepository.findAll()).thenReturn(deviceGroups);
        List<DeviceGroupDto> deviceGroupDtoList = deviceGroupService.findAllGroups();

        assertThat(deviceGroupDtoList).isNotNull();
        assertThat(deviceGroupDtoList.get(0).name()).isEqualTo("First");
        assertThat(deviceGroupDtoList.get(1).name()).isEqualTo("Second");


    }

}