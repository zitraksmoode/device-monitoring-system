package com.pet.device_monitoring_system.services.userService;

import com.pet.device_monitoring_system.dto.UserDto;
import com.pet.device_monitoring_system.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto findUserByName(String userName);
    UserDto createUser(UserDto userDto);
    UserDto findUserById(UUID id);
    List<UserDto> findAllUsers();
}
