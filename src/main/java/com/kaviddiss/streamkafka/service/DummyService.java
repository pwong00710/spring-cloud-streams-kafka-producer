package com.kaviddiss.streamkafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

@MessageEndpoint
@Slf4j
public class DummyService {
    
    @ServiceActivator(inputChannel = "dummyOutputChannel")
    public void drop(Message message) {
        log.info("Drop dummy message {}", message.getPayload());
    }
    
}
