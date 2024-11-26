package br.com.ligabue.communication_api.service.impl;

import br.com.ligabue.communication_api.entity.CommunicationScheduled;
import br.com.ligabue.communication_api.service.RabbitMQProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducerServiceImpl implements RabbitMQProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;


    @Override
    public void sendMessage(CommunicationScheduled communicationScheduled) {
        rabbitTemplate.convertAndSend(exchange, routingKey, communicationScheduled);
        log.info("Mensagem enviada para a fila: {}", communicationScheduled);
    }
}
