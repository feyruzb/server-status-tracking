package dev.feyruz.serverstatustracking.controller;

import dev.feyruz.serverstatustracking.entity.MonitoredServer;
import dev.feyruz.serverstatustracking.service.MonitoredServerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class MonitoredServerController {

    private final MonitoredServerService service;

    public MonitoredServerController(MonitoredServerService service) {
        this.service = service;
    }

    @GetMapping
    public List<MonitoredServer> findAll() {
        return service.findAll();
    }

    @PostMapping
    public MonitoredServer create(@RequestBody MonitoredServer server) {
        return service.create(server);
    }


}
