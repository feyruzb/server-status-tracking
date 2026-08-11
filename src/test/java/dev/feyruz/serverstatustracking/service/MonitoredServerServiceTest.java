package dev.feyruz.serverstatustracking.service;

import dev.feyruz.serverstatustracking.dto.ServerResponse;
import dev.feyruz.serverstatustracking.entity.MonitoredServer;
import dev.feyruz.serverstatustracking.exception.ServerNotFoundException;
import dev.feyruz.serverstatustracking.repository.MonitoredServerRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MonitoredServerServiceTest {
    MonitoredServerRepository repository = mock(MonitoredServerRepository.class);
    MonitoredServerService service = new MonitoredServerService(repository);

    @Test
    void testFindByIdSuccess(){
        MonitoredServer server = new MonitoredServer("192.168.1.1", "Server1", "Description");
        when(repository.findById(1L)).thenReturn(Optional.of(server));

        ServerResponse result = service.findById(1L);

        assertEquals("Server1", result.name());

    }

    @Test
    void testFindByIdFail() throws Exception {

        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ServerNotFoundException.class, () -> service.findById(999L));
    }

    @Test
    void testDeleteSuccess() {
        when(repository.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void testDeleteFail() {
        when(repository.existsById(999L)).thenReturn(false);
        assertThrows(ServerNotFoundException.class, () -> service.delete(999L));
        verify(repository, never()).deleteById(999L);
    }

}
