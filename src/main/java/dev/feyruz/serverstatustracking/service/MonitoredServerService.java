package dev.feyruz.serverstatustracking.service;
import dev.feyruz.serverstatustracking.entity.MonitoredServer;
import dev.feyruz.serverstatustracking.repository.MonitoredServerRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MonitoredServerService {

    private final MonitoredServerRepository repository;

    public MonitoredServerService(MonitoredServerRepository repository){
        this.repository = repository;
    }

    public List<MonitoredServer> findAll() {
        // use the repository
        return repository.findAll();
    }

    public MonitoredServer create(MonitoredServer server) {
        // use the repository
        return repository.save(server);
    }
}
