package com.pet.device_monitoring_system.controllers;

import com.pet.device_monitoring_system.dto.UserDto;
import com.pet.device_monitoring_system.services.userService.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserServiceImpl userService;


    @Test
    void getAllUsers() throws Exception {
        List<UserDto> userDtoList = List.of(
                new UserDto("First"),
                new UserDto("Second"));
        when(userService.findAllUsers()).
                thenReturn(userDtoList);

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("First"))
                .andExpect(jsonPath("$[1].username").value("Second"));
    }

    @Test
    void getUserById() throws Exception {
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        UserDto response = new UserDto("admin");

        when(userService.findUserById(id))
                .thenReturn(response);

        mockMvc.perform(get("/api/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"));

    }

    @Test
    void createUser() throws Exception {
        UserDto userDto = new UserDto("Create");

        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "username": "Create"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Create"));
    }

    @Test
    void getUserByName() throws Exception {
        String name = "TestName";
        UserDto userDto = new UserDto(name);
        when(userService.findUserByName(any(String.class))).thenReturn(userDto);

        mockMvc.perform(get("/api/user/by-name/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("TestName"));
    }
}