package com.pet.device_monitoring_system.repository;

import com.pet.device_monitoring_system.entity.PingLog;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PingLogRepository extends CrudRepository<PingLog, UUID> {
}
