package com.pet.device_monitoring_system.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import com.pet.device_monitoring_system.dto.DeviceGroupDto;
import com.pet.device_monitoring_system.services.deviceGroupService.DeviceGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceGroupController.class)
class DeviceGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeviceGroupService deviceGroupService;

    @Autowired
    private ObjectMapper objectMapper;

    private DeviceGroupDto sampleDto;
    private UUID sampleId;

    @BeforeEach
    void setUp() {
        sampleDto = new DeviceGroupDto("Test Name", "CODE123", "Test Description", "Moscow");
        sampleId = UUID.randomUUID();
    }

    @Test
    void createDeviceGroup_ShouldReturn200AndDto() throws Exception {
        when(deviceGroupService.createDeviceGroup(any(DeviceGroupDto.class))).thenReturn(sampleDto);

        mockMvc.perform(post("/api/device-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Name"))
                .andExpect(jsonPath("$.code").value("CODE123"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.location").value("Moscow"));
    }

    @Test
    void getDeviceGroupById_ShouldReturn200AndDto() throws Exception {
        when(deviceGroupService.findGroupById(sampleId)).thenReturn(sampleDto);

        mockMvc.perform(get("/api/device-group/{id}", sampleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Name"))
                .andExpect(jsonPath("$.code").value("CODE123"));
    }

    @Test
    void getAllDeviceGroup_ShouldReturnList() throws Exception {
        List<DeviceGroupDto> dtoList = List.of(sampleDto);
        when(deviceGroupService.findAllGroups()).thenReturn(dtoList);

        mockMvc.perform(get("/api/device-group/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Name"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getDeviceGroupByCode_ShouldReturn200AndDto() throws Exception {
        String code = "CODE123";
        when(deviceGroupService.findGroupByCode(code)).thenReturn(sampleDto);

        mockMvc.perform(get("/api/device-group/code/{code}", code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(code));
    }

    @Test
    void updateDeviceGroup_ShouldReturn200AndUpdatedDto() throws Exception {
        when(deviceGroupService.updateGroup(eq(sampleId), any(DeviceGroupDto.class))).thenReturn(sampleDto);

        mockMvc.perform(put("/api/device-group/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Name"));
    }

    @Test
    void deleteDeviceGroup_ShouldReturn200() throws Exception {
        doNothing().when(deviceGroupService).deleteGroup(sampleId);

        mockMvc.perform(delete("/api/device-group/{id}", sampleId))
                .andExpect(status().isOk());
    }
}