package dev.feyruz.serverstatustracking.controller;

import dev.feyruz.serverstatustracking.dto.CheckResultResponse;
import dev.feyruz.serverstatustracking.dto.CreateServerRequest;
import dev.feyruz.serverstatustracking.dto.ServerResponse;
import dev.feyruz.serverstatustracking.entity.CheckResult;
import dev.feyruz.serverstatustracking.service.HealthCheckService;
import dev.feyruz.serverstatustracking.service.MonitoredServerService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class MonitoredServerController {

    private final MonitoredServerService service;
    private final HealthCheckService healthCheckService;

    public MonitoredServerController(MonitoredServerService service, HealthCheckService healthCheckService) {
        this.service = service;
        this.healthCheckService = healthCheckService;
    }

    @GetMapping
    public List<ServerResponse> findAll() {
        return service.findAll();
    }

    @PostMapping
    public ServerResponse create(@Valid @RequestBody CreateServerRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ServerResponse update(@PathVariable Long id, @Valid @RequestBody CreateServerRequest request){
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/check")
    public CheckResultResponse checkNow(@PathVariable Long id) {
        return healthCheckService.check(id);
    }

    @GetMapping("/{id}/history")
    public List<CheckResultResponse> history(@PathVariable Long id) {
        return healthCheckService.history(id);
    }
}
