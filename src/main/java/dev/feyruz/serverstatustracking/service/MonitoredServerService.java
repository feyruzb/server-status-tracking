package dev.feyruz.serverstatustracking.service;
import dev.feyruz.serverstatustracking.dto.CreateServerRequest;
import dev.feyruz.serverstatustracking.dto.ServerResponse;
import dev.feyruz.serverstatustracking.entity.MonitoredServer;
import dev.feyruz.serverstatustracking.exception.ServerNotFoundException;
import dev.feyruz.serverstatustracking.repository.MonitoredServerRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MonitoredServerService {

    private final MonitoredServerRepository repository;

    public MonitoredServerService(MonitoredServerRepository repository){
        this.repository = repository;
    }

    public ServerResponse findById(Long id) {
        MonitoredServer server = repository.findById(id)
                .orElseThrow(() -> new ServerNotFoundException(id));
        return toResponse(server);
    }

    public List<ServerResponse> findAll() {

        List<MonitoredServer> servers = repository.findAll();
        List<ServerResponse> result = new ArrayList<>();

        for (MonitoredServer server : servers) {
            result.add(toResponse(server));
        }

        return result;
    }

    public ServerResponse create(CreateServerRequest request) {

        MonitoredServer server = new MonitoredServer(
                request.ip(),
                request.port(),
                request.name(),
                request.description(),
                request.enabled()
        );

        MonitoredServer saved = repository.save(server);

        return toResponse(saved);
    }

    public ServerResponse update(Long id, CreateServerRequest request) {

        MonitoredServer currentConfiguration = repository.findById(id)
                .orElseThrow(() -> new ServerNotFoundException(id));

        currentConfiguration.setIp(request.ip());
        currentConfiguration.setPort(request.port());
        currentConfiguration.setName(request.name());
        currentConfiguration.setDescription(request.description());
        currentConfiguration.setEnabled(request.enabled());

        MonitoredServer saved = repository.save(currentConfiguration);

        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ServerNotFoundException(id);
        }
        repository.deleteById(id);
    }

    //    Helper methods
    private ServerResponse toResponse(MonitoredServer server) {
        return new ServerResponse(
                server.getId(),
                server.getIp(),
                server.getPort(),
                server.getName(),
                server.getDescription(),
                server.isEnabled()
        );
    }

}
