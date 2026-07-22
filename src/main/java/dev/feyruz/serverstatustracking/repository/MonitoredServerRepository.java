package dev.feyruz.serverstatustracking.repository;

import dev.feyruz.serverstatustracking.entity.MonitoredServer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredServerRepository extends JpaRepository<MonitoredServer, Long> {

}