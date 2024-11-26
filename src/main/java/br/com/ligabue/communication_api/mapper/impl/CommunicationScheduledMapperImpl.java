package br.com.ligabue.communication_api.mapper.impl;

import br.com.ligabue.communication_api.dto.CommunicationScheduledDto;
import br.com.ligabue.communication_api.entity.CommunicationScheduled;
import br.com.ligabue.communication_api.enumeration.CommunicationScheduledStatus;
import br.com.ligabue.communication_api.enumeration.CommunicationType;
import br.com.ligabue.communication_api.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class CommunicationScheduledMapperImpl implements Mapper<CommunicationScheduledDto, CommunicationScheduled> {

    @Override
    public CommunicationScheduled mapToEntity(CommunicationScheduledDto dto) {
        return CommunicationScheduled.builder()
                .id(dto.getId())
                .type(CommunicationType.fromString(dto.getType()))
                .status(CommunicationScheduledStatus.fromString(dto.getStatus()))
                .destination(dto.getDestination())
                .scheduledDatetime(ZonedDateTime.parse(dto.getScheduledDatetime()))
                .message(dto.getMessage())
                .build();
    }

    @Override
    public CommunicationScheduledDto mapToDto(CommunicationScheduled entity) {
        return CommunicationScheduledDto.builder()
                .id(entity.getId())
                .type(CommunicationType.toString(entity.getType()))
                .status(CommunicationScheduledStatus.toString(entity.getStatus()))
                .destination(entity.getDestination())
                .scheduledDatetime(entity.getScheduledDatetime().toString())
                .message(entity.getMessage())
                .build();
    }
}
