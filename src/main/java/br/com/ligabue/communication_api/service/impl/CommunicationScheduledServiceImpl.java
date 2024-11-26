package br.com.ligabue.communication_api.service.impl;

import br.com.ligabue.communication_api.dto.CommunicationScheduledDto;
import br.com.ligabue.communication_api.entity.CommunicationScheduled;
import br.com.ligabue.communication_api.enumeration.CommunicationScheduledStatus;
import br.com.ligabue.communication_api.exceptionhandling.exceptions.MissingParametersException;
import br.com.ligabue.communication_api.mapper.impl.CommunicationScheduledMapperImpl;
import br.com.ligabue.communication_api.repository.CommunicationScheduledRepository;
import br.com.ligabue.communication_api.service.CommunicationScheduledService;
import br.com.ligabue.communication_api.service.RabbitMQProducerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommunicationScheduledServiceImpl implements CommunicationScheduledService {

    CommunicationScheduledRepository communicationScheduledRepository;
    CommunicationScheduledMapperImpl communicationScheduledMapper;
    RabbitMQProducerService rabbitMQProducerService;

    @Override
    public CommunicationScheduledDto schedule(CommunicationScheduledDto communicationScheduledDto) {

        if (communicationScheduledDto.getDestination() == null) {
            throw new MissingParametersException("O campo 'destination' é obrigatório.");
        }
        if (communicationScheduledDto.getScheduledDatetime() == null) {
            throw new MissingParametersException("O campo 'scheduledDatetime' é obrigatório.");
        }
        if (communicationScheduledDto.getMessage() == null) {
            throw new MissingParametersException("O campo 'message' é obrigatório.");
        }

        communicationScheduledDto.setStatus(CommunicationScheduledStatus.PENDING.name());
        CommunicationScheduled communicationScheduled = communicationScheduledMapper.mapToEntity(communicationScheduledDto);

        CommunicationScheduledDto savedObjectDto = communicationScheduledMapper.mapToDto(communicationScheduledRepository.save(communicationScheduled));

        rabbitMQProducerService.sendMessage(communicationScheduled);

        return savedObjectDto;
    }

    @Override
    public CommunicationScheduledDto getById(Long id) {
        return communicationScheduledMapper.mapToDto(communicationScheduledRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comunicação agendada com ID " + id + " não encontrada.")));
    }

    @Override
    public void cancel(Long id) {
        int updatedRows = communicationScheduledRepository.updateStatusById(id, CommunicationScheduledStatus.CANCELLED);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("Comunicação agendada com ID " + id + " não encontrada.");
        }
    }
}
