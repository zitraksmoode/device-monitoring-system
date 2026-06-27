package com.pet.device_monitoring_system.services.userService;

import com.pet.device_monitoring_system.dto.UserDto;
import com.pet.device_monitoring_system.entity.User;
import com.pet.device_monitoring_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findUserByName(String userName) {
        User user = userRepository.findUserByUsername(userName);
        return new UserDto(user.getUsername());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.username());
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getUsername());
    }

    @Override
    public UserDto findUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserDto(user.getUsername());
    }

    @Override
    public List<UserDto> findAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : users){
            userDtoList.add(new UserDto(user.getUsername()));
        }
        return userDtoList;
    }

}
