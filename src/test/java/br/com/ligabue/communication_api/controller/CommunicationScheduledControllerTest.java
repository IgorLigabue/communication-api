package br.com.ligabue.communication_api.controller;

import br.com.ligabue.communication_api.dto.CommunicationScheduledDto;
import br.com.ligabue.communication_api.service.CommunicationScheduledService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CommunicationScheduledControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommunicationScheduledService communicationScheduledService;

    @InjectMocks
    private CommunicationScheduledController communicationScheduledController;

    private ObjectMapper objectMapper;

    private CommunicationScheduledDto communicationScheduledDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(communicationScheduledController).build();

        objectMapper = new ObjectMapper();

        communicationScheduledDto = new CommunicationScheduledDto();
        communicationScheduledDto.setId(1L);
        communicationScheduledDto.setType("EMAIL");
        communicationScheduledDto.setStatus("PENDING");
        communicationScheduledDto.setDestination("usuario@exemplo.com");
        communicationScheduledDto.setScheduledDatetime("2024-12-01T10:00:00Z");
        communicationScheduledDto.setMessage("Olá, esta é uma mensagem agendada.");
    }

    @Test
    void testSchedule() throws Exception {
        when(communicationScheduledService.schedule(any(CommunicationScheduledDto.class)))
                .thenReturn(communicationScheduledDto);

        mockMvc.perform(post("/communications/scheduled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(communicationScheduledDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("EMAIL"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.destination").value("usuario@exemplo.com"))
                .andExpect(jsonPath("$.scheduledDatetime").value("2024-12-01T10:00:00Z"))
                .andExpect(jsonPath("$.message").value("Olá, esta é uma mensagem agendada."));
    }

    @Test
    void testGetById() throws Exception {
        when(communicationScheduledService.getById(anyLong())).thenReturn(communicationScheduledDto);

        mockMvc.perform(get("/communications/scheduled/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("EMAIL"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.destination").value("usuario@exemplo.com"))
                .andExpect(jsonPath("$.scheduledDatetime").value("2024-12-01T10:00:00Z"))
                .andExpect(jsonPath("$.message").value("Olá, esta é uma mensagem agendada."));
    }

    @Test
    void testCancelById() throws Exception {
        doNothing().when(communicationScheduledService).cancel(anyLong());

        mockMvc.perform(patch("/communications/scheduled/{id}/cancel", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
