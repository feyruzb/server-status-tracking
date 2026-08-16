package dev.feyruz.serverstatustracking.repository;

import dev.feyruz.serverstatustracking.entity.MonitoredServer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**do
 * Data access for monitored servers.
 * Spring Data generates the implementation at runtime.
 */
public interface MonitoredServerRepository extends JpaRepository<MonitoredServer, Long> {

    /**
     * Returns only servers with monitoring enabled, used by the scheduler.
     */
    List<MonitoredServer> findByEnabledTrue();
}