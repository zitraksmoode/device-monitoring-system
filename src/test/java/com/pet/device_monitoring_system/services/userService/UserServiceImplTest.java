package com.pet.device_monitoring_system.services.userService;

import com.pet.device_monitoring_system.dto.UserDto;
import com.pet.device_monitoring_system.entity.User;
import com.pet.device_monitoring_system.repository.UserRepository;
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
class UserServiceImplTest {
    @InjectMocks
    private  UserServiceImpl userService;
    @Mock
    private  UserRepository userRepository;


    @Test
    void createdUser_whenNameUserCorrect_shouldCreatedUser() {
        UserDto userDto = new UserDto("Test name");
        User savedUser = new User();
        savedUser.setUsername(userDto.username());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.createUser(userDto);
        assertThat(result).isNotNull();
    }
    @Test
    void findUser_whenNameUserIsCorrect_shouldCorrectUser(){
        String userName = "CorrectName";
        User user = new User();
        user.setUsername(userName);
        when(userRepository.findUserByUsername(userName)).thenReturn(user);

        UserDto result = userService.findUserByName(userName);
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo(userName);
    }
    @Test
    void findUser_whenIdUserIsCorrect_shouldCorrectUser(){
        User user = new User();
        UUID uuid = UUID.randomUUID();
        user.setId(uuid);
        user.setUsername("TestNameForThisSituation");
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));

        UserDto userDto = userService.findUserById(uuid);

        assertThat(userDto).isNotNull();
        assertThat(userDto.username()).isEqualTo("TestNameForThisSituation");
    }
    @Test
    void findAllUsers_whenAllUsersCorrect_shouldReturnListUsers(){
        User firstUser = new User();
        firstUser.setUsername("User One");
        User secondUser = new User();
        secondUser.setUsername("User Two");
        User threeUser = new User();
        threeUser.setUsername("User Three");
        List<User> users = List.of(firstUser, secondUser,threeUser);

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> userDtoList = userService.findAllUsers();
        assertThat(userDtoList).isNotNull();
        assertThat(userDtoList).hasSize(3);
        assertThat(userDtoList.get(0).username()).isEqualTo("User One");
        assertThat(userDtoList.get(1).username()).isEqualTo("User Two");
        assertThat(userDtoList.get(2).username()).isEqualTo("User Three");


    }

}
//    Given — подготовка условий
//    When — вызываем тестируемый метод
//    Then — проверяем результат