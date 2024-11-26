package br.com.ligabue.communication_api.service.impl;

import br.com.ligabue.communication_api.dto.CommunicationScheduledDto;
import br.com.ligabue.communication_api.entity.CommunicationScheduled;
import br.com.ligabue.communication_api.enumeration.CommunicationScheduledStatus;
import br.com.ligabue.communication_api.exceptionhandling.exceptions.MissingParametersException;
import br.com.ligabue.communication_api.mapper.impl.CommunicationScheduledMapperImpl;
import br.com.ligabue.communication_api.repository.CommunicationScheduledRepository;
import br.com.ligabue.communication_api.service.RabbitMQProducerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommunicationScheduledServiceImplTest {

    @Mock
    private CommunicationScheduledRepository communicationScheduledRepository;

    @Mock
    private CommunicationScheduledMapperImpl communicationScheduledMapper;

    @Mock
    private RabbitMQProducerService rabbitMQProducerService;

    @InjectMocks
    private CommunicationScheduledServiceImpl communicationScheduledService;

    private CommunicationScheduledDto communicationScheduledDto;
    private CommunicationScheduled communicationScheduled;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        communicationScheduledDto = CommunicationScheduledDto.builder()
                .destination("test@example.com")
                .scheduledDatetime(String.valueOf(ZonedDateTime.now().plusDays(1)))
                .message("Test message")
                .build();

        communicationScheduled = CommunicationScheduled.builder()
                .id(1L)
                .destination("test@example.com")
                .scheduledDatetime(ZonedDateTime.now().plusDays(1))
                .message("Test message")
                .status(CommunicationScheduledStatus.PENDING)
                .build();
    }

    @Test
    void scheduleShouldSaveCommunicationWhenValidDtoProvided() {
        when(communicationScheduledMapper.mapToEntity(any(CommunicationScheduledDto.class))).thenReturn(communicationScheduled);
        when(communicationScheduledRepository.save(any(CommunicationScheduled.class))).thenReturn(communicationScheduled);
        when(communicationScheduledMapper.mapToDto(any(CommunicationScheduled.class))).thenReturn(communicationScheduledDto);

        CommunicationScheduledDto result = communicationScheduledService.schedule(communicationScheduledDto);

        assertNotNull(result);
        assertEquals(communicationScheduledDto.getDestination(), result.getDestination());
        verify(rabbitMQProducerService, times(1)).sendMessage(any(CommunicationScheduled.class));
    }

    @Test
    void scheduleShouldThrowMissingParametersExceptionWhenDestinationIsNull() {
        communicationScheduledDto.setDestination(null);

        MissingParametersException exception = assertThrows(MissingParametersException.class, () -> {
            communicationScheduledService.schedule(communicationScheduledDto);
        });
        assertEquals("O campo 'destination' é obrigatório.", exception.getMessage());
    }

    @Test
    void scheduleShouldThrowMissingParametersExceptionWhenScheduledDatetimeIsNull() {
        communicationScheduledDto.setScheduledDatetime(null);

        MissingParametersException exception = assertThrows(MissingParametersException.class, () -> {
            communicationScheduledService.schedule(communicationScheduledDto);
        });
        assertEquals("O campo 'scheduledDatetime' é obrigatório.", exception.getMessage());
    }

    @Test
    void scheduleShouldThrowMissingParametersExceptionWhenMessageIsNull() {
        communicationScheduledDto.setMessage(null);

        MissingParametersException exception = assertThrows(MissingParametersException.class, () -> {
            communicationScheduledService.schedule(communicationScheduledDto);
        });
        assertEquals("O campo 'message' é obrigatório.", exception.getMessage());
    }

    @Test
    void getByIdShouldReturnCommunicationWhenIdExists() {
        when(communicationScheduledRepository.findById(1L)).thenReturn(Optional.of(communicationScheduled));
        when(communicationScheduledMapper.mapToDto(any(CommunicationScheduled.class))).thenReturn(communicationScheduledDto);

        CommunicationScheduledDto result = communicationScheduledService.getById(1L);

        assertNotNull(result);
        assertEquals(communicationScheduledDto.getDestination(), result.getDestination());
    }

    @Test
    void getByIdShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        when(communicationScheduledRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            communicationScheduledService.getById(1L);
        });
        assertEquals("Comunicação agendada com ID 1 não encontrada.", exception.getMessage());
    }

    @Test
    void cancelShouldUpdateStatusToCancelledWhenIdExists() {
        when(communicationScheduledRepository.updateStatusById(1L, CommunicationScheduledStatus.CANCELLED)).thenReturn(1);

        assertDoesNotThrow(() -> communicationScheduledService.cancel(1L));
    }

    @Test
    void cancelShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        when(communicationScheduledRepository.updateStatusById(1L, CommunicationScheduledStatus.CANCELLED)).thenReturn(0);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            communicationScheduledService.cancel(1L);
        });
        assertEquals("Comunicação agendada com ID 1 não encontrada.", exception.getMessage());
    }
}
