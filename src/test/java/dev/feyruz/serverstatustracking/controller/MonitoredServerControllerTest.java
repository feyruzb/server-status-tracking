package dev.feyruz.serverstatustracking.controller;

import dev.feyruz.serverstatustracking.dto.CheckResultResponse;
import dev.feyruz.serverstatustracking.dto.ServerResponse;
import dev.feyruz.serverstatustracking.entity.CheckStatus;
import dev.feyruz.serverstatustracking.exception.ServerNotFoundException;
import dev.feyruz.serverstatustracking.service.HealthCheckService;
import dev.feyruz.serverstatustracking.service.MonitoredServerService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonitoredServerController.class)
class MonitoredServerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MonitoredServerService service;

    @MockitoBean
    private HealthCheckService healthCheckService;

    @Test
    void findAll_returnsServers() throws Exception {
        ServerResponse server = new ServerResponse(1L, "192.168.1.1", 80, "Server1", "Description", true);

        when(service.findAll()).thenReturn(List.of(server));

        mockMvc.perform(get("/api/servers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Server1"));
    }

    @Test
    void findById_returnsNotFoundWhenMissing() throws Exception {
        when(service.findById(999L)).thenThrow(new ServerNotFoundException(999L));

        mockMvc.perform(get("/api/servers/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returnsBadRequestWhenIpIsBlank() throws Exception {
        mockMvc.perform(post("/api/servers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ip\":\"\",\"port\":\"80\",\"name\":\"test\",\"description\":\"test\",\"enabled\":true}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void create_returnsBadRequestWhenPortOutOfRange() throws Exception {
        mockMvc.perform(post("/api/servers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ip\":\"example.com\",\"port\":99999,\"name\":\"test\",\"description\":\"test\",\"enabled\":true}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void latest_returnsResultWhenPresent() throws Exception {

        CheckResultResponse result = new CheckResultResponse(
                1L, 1L, CheckStatus.UP, 42L, Instant.now());

        when(healthCheckService.latestResult(1L)).thenReturn(Optional.of(result));

        mockMvc.perform(get("/api/servers/1/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void latest_returnsNotFoundWhenNoChecksYet() throws Exception {

        when(healthCheckService.latestResult(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/servers/1/latest"))
                .andExpect(status().isNotFound());
    }
}
