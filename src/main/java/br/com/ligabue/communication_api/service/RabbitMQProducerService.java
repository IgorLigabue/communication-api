package br.com.ligabue.communication_api.service;

import br.com.ligabue.communication_api.entity.CommunicationScheduled;

public interface RabbitMQProducerService {

    public void sendMessage(CommunicationScheduled communicationScheduled);
}
