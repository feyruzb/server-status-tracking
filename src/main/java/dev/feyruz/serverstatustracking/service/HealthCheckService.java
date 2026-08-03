package dev.feyruz.serverstatustracking.service;

import dev.feyruz.serverstatustracking.dto.CheckResultResponse;
import dev.feyruz.serverstatustracking.entity.CheckResult;
import dev.feyruz.serverstatustracking.entity.CheckStatus;
import dev.feyruz.serverstatustracking.entity.MonitoredServer;
import dev.feyruz.serverstatustracking.exception.ServerNotFoundException;
import dev.feyruz.serverstatustracking.repository.CheckResultRepository;
import dev.feyruz.serverstatustracking.repository.MonitoredServerRepository;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class HealthCheckService {

    private final MonitoredServerRepository repository;
    private final CheckResultRepository repositoryHealth;
    private final RestClient client;

    public HealthCheckService(MonitoredServerRepository repository,
                              CheckResultRepository repositoryHealth) {
        this.repository = repository;
        this.repositoryHealth = repositoryHealth;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(3));
        factory.setReadTimeout(Duration.ofSeconds(3));

        this.client = RestClient.builder()
                .requestFactory(factory)
                .build();
    }

    public List<CheckResultResponse> history(Long serverId) {

        if (!repository.existsById(serverId)) {
            throw new ServerNotFoundException(serverId);
        }

        List<CheckResult> results = repositoryHealth.findByServerIdOrderByCheckedAtDesc(serverId);
        List<CheckResultResponse> response = new ArrayList<>();

        for (CheckResult result : results) {
            response.add(toResponse(result));
        }

        return response;
    }

    private CheckResultResponse toResponse(CheckResult result) {
        return new CheckResultResponse(
                result.getId(),
                result.getServer().getId(),
                result.getStatus(),
                result.getResponseTimeMs(),
                result.getCheckedAt()
        );
    }

    public CheckResultResponse check(Long serverId) {

        MonitoredServer server = repository.findById(serverId)
                .orElseThrow(() -> new ServerNotFoundException(serverId));

        long start = System.nanoTime();
        CheckStatus status;

        try {
            client.get()
                    .uri("http://" + server.getIp())
                    .retrieve()
                    .toBodilessEntity();
            status = CheckStatus.UP;
        } catch (Exception e) {
            status = CheckStatus.DOWN;
        }

        long elapsed = (System.nanoTime() - start) / 1_000_000;

        CheckResult row = new CheckResult(server, status, elapsed, Instant.now());

        CheckResult saved = repositoryHealth.save(row);

        return new CheckResultResponse(
                saved.getId(),
                saved.getServer().getId(),
                saved.getStatus(),
                saved.getResponseTimeMs(),
                saved.getCheckedAt()
        );
    }

    @Scheduled(fixedRate = 60000)
    public void checkAllServers() {
        for (MonitoredServer server : repository.findAll()) {
            check(server.getId());
        }
    }
}