package dev.feyruz.serverstatustracking;

import dev.feyruz.serverstatustracking.dto.ServerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MonitoredSserverIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createThenFetch_returnsTheServer() throws Exception {

        // 1. POST a server, capture the response
        mockMvc.perform(post("/api/servers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ip\":\"192.168.1.1\",\"name\":\"Server1\",\"description\":\"Description\"}"))
                .andExpect(status().isOk());


        // 2. GET the list, assert the server is in it

        mockMvc.perform(get("/api/servers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Server1"))
                .andExpect(jsonPath("$[0].ip").value("192.168.1.1"));
    }


}
