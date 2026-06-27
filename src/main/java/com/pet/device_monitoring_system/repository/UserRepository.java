package com.pet.device_monitoring_system.repository;

import com.pet.device_monitoring_system.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    User findUserByUsername(String username);
}
