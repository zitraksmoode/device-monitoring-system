package com.pet.device_monitoring_system.controllers;

import com.pet.device_monitoring_system.dto.DeviceDto;
import com.pet.device_monitoring_system.entity.DeviceGroup;
import com.pet.device_monitoring_system.entity.DeviceType;
import com.pet.device_monitoring_system.entity.StatusType;
import com.pet.device_monitoring_system.services.deviceService.DeviceService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DeviceService deviceService;

    @Test
    void createDevice_whenRequestIsValid_shouldReturnCreatedDevice() throws Exception {
        DeviceDto request = createDeviceDto("ATM-001", "192.168.1.10", StatusType.UNKNOWN);
        DeviceDto response = createDeviceDto("ATM-001", "192.168.1.10", StatusType.UNKNOWN);

        when(deviceService.createDevice(any(DeviceDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ATM-001"))
                .andExpect(jsonPath("$.ipAddress").value("192.168.1.10"))
                .andExpect(jsonPath("$.isActive").value(true))
                .andExpect(jsonPath("$.deviceType").value("ATM"))
                .andExpect(jsonPath("$.status").value("UNKNOWN"));

        verify(deviceService).createDevice(any(DeviceDto.class));
    }

    @Test
    void getDeviceById_whenDeviceExists_shouldReturnDevice() throws Exception {
        UUID id = UUID.randomUUID();
        DeviceDto response = createDeviceDto("ATM-001", "192.168.1.10", StatusType.GOOD);

        when(deviceService.findDeviceById(id)).thenReturn(response);

        mockMvc.perform(get("/api/devices/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ATM-001"))
                .andExpect(jsonPath("$.ipAddress").value("192.168.1.10"))
                .andExpect(jsonPath("$.status").value("GOOD"));

        verify(deviceService).findDeviceById(id);
    }

    @Test
    void getAllDevice_whenDevicesExist_shouldReturnDevicesList() throws Exception {
        DeviceDto device1 = createDeviceDto("ATM-001", "192.168.1.10", StatusType.GOOD);
        DeviceDto device2 = createDeviceDto("ATM-002", "192.168.1.11", StatusType.BAD);

        when(deviceService.findAllDevices()).thenReturn(List.of(device1, device2));

        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ATM-001"))
                .andExpect(jsonPath("$[0].ipAddress").value("192.168.1.10"))
                .andExpect(jsonPath("$[0].status").value("GOOD"))
                .andExpect(jsonPath("$[1].name").value("ATM-002"))
                .andExpect(jsonPath("$[1].ipAddress").value("192.168.1.11"))
                .andExpect(jsonPath("$[1].status").value("BAD"));

        verify(deviceService).findAllDevices();
    }

    @Test
    void getAllDeviceByGroupId_whenDevicesExist_shouldReturnDevicesList() throws Exception {
        // given
        UUID groupId = UUID.randomUUID();

        DeviceDto device1 = createDeviceDto("ATM-001", "192.168.1.10", StatusType.GOOD);
        DeviceDto device2 = createDeviceDto("ATM-002", "192.168.1.11", StatusType.UNKNOWN);

        when(deviceService.findDevicesByGroupId(groupId)).thenReturn(List.of(device1, device2));

        mockMvc.perform(get("/api/devices/group/{id}", groupId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ATM-001"))
                .andExpect(jsonPath("$[0].status").value("GOOD"))
                .andExpect(jsonPath("$[1].name").value("ATM-002"))
                .andExpect(jsonPath("$[1].status").value("UNKNOWN"));

        verify(deviceService).findDevicesByGroupId(groupId);
    }

    @Test
    void getAllDeviceByStatus_whenDevicesExist_shouldReturnDevicesList() throws Exception {
        DeviceDto device1 = createDeviceDto("ATM-001", "192.168.1.10", StatusType.BAD);
        DeviceDto device2 = createDeviceDto("ATM-002", "192.168.1.11", StatusType.BAD);

        when(deviceService.findDevicesByStatus(StatusType.BAD)).thenReturn(List.of(device1, device2));

        mockMvc.perform(get("/api/devices/status/{status}", StatusType.BAD))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ATM-001"))
                .andExpect(jsonPath("$[0].status").value("BAD"))
                .andExpect(jsonPath("$[1].name").value("ATM-002"))
                .andExpect(jsonPath("$[1].status").value("BAD"));

        verify(deviceService).findDevicesByStatus(StatusType.BAD);
    }

    @Test
    void updateDevice_whenRequestIsValid_shouldReturnUpdatedDevice() throws Exception {
        UUID id = UUID.randomUUID();

        DeviceDto request = createDeviceDto("ATM-UPDATED", "10.0.0.5", StatusType.GOOD);
        DeviceDto response = createDeviceDto("ATM-UPDATED", "10.0.0.5", StatusType.GOOD);

        when(deviceService.updateDevice(eq(id), any(DeviceDto.class))).thenReturn(response);
        mockMvc.perform(put("/api/devices/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ATM-UPDATED"))
                .andExpect(jsonPath("$.ipAddress").value("10.0.0.5"))
                .andExpect(jsonPath("$.status").value("GOOD"));

        verify(deviceService).updateDevice(eq(id), any(DeviceDto.class));
    }

    @Test
    void deleteDevice_whenDeviceExists_shouldReturnOk() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/devices/{id}", id))
                .andExpect(status().isOk());

        verify(deviceService).deleteDevice(id);
    }

    @Test
    void updateDeviceStatus_whenRequestIsValid_shouldReturnUpdatedDevice() throws Exception {
        UUID id = UUID.randomUUID();

        DeviceDto response = createDeviceDto("ATM-001", "192.168.1.10", StatusType.BAD);

        when(deviceService.changeDeviceStatus(id, StatusType.BAD)).thenReturn(response);

        mockMvc.perform(patch("/api/devices/{id}/status", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(StatusType.BAD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ATM-001"))
                .andExpect(jsonPath("$.ipAddress").value("192.168.1.10"))
                .andExpect(jsonPath("$.status").value("BAD"));

        verify(deviceService).changeDeviceStatus(id, StatusType.BAD);
    }

    private DeviceDto createDeviceDto(String name, String ipAddress, StatusType status) {
        return new DeviceDto(
                name,
                ipAddress,
                new DeviceGroup(),
                true,
                DeviceType.ATM,
                status
        );
    }
}