package com.pet.device_monitoring_system.controllers;

import com.pet.device_monitoring_system.dto.UserDto;
import com.pet.device_monitoring_system.services.userService.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
class UserController {
    private final UserServiceImpl userService;

    UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDto> getAllUsers(){
        return userService.findAllUsers();
    }
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID id){
        return userService.findUserById(id);
    }
    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
    @GetMapping("/by-name/{name}")
    public UserDto getUserByName(@PathVariable String name) {
        return  userService.findUserByName(name);
    }
}
