package com.pet.device_monitoring_system.repository;

import com.pet.device_monitoring_system.entity.Alert;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AlertRepository extends CrudRepository<Alert, UUID> {
}
