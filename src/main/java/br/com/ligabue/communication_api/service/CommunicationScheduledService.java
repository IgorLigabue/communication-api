package br.com.ligabue.communication_api.service;

import br.com.ligabue.communication_api.dto.CommunicationScheduledDto;

public interface CommunicationScheduledService {

    CommunicationScheduledDto schedule(CommunicationScheduledDto communicationScheduledDto);

    CommunicationScheduledDto getById(Long id);

    void cancel(Long id);
}
