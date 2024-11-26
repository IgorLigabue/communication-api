package br.com.ligabue.communication_api.service.impl;

import br.com.ligabue.communication_api.entity.CommunicationScheduled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

public class RabbitMQProducerServiceImplTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQProducerServiceImpl rabbitMQProducerService;

    private static final String EXCHANGE = "exchange-test";
    private static final String ROUTING_KEY = "routingkey-test";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rabbitMQProducerService = new RabbitMQProducerServiceImpl(rabbitTemplate);
        rabbitMQProducerService.exchange = EXCHANGE;
        rabbitMQProducerService.routingKey = ROUTING_KEY;
    }

    @Test
    public void testSendMessage() {
        CommunicationScheduled communicationScheduled = new CommunicationScheduled();
        communicationScheduled.setMessage("Test message");
        rabbitMQProducerService.sendMessage(communicationScheduled);

        verify(rabbitTemplate, times(1)).convertAndSend(EXCHANGE, ROUTING_KEY, communicationScheduled);
    }
}
