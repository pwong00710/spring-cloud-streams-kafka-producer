package com.kaviddiss.streamkafka.config;

import com.kaviddiss.streamkafka.model.Goodbyes;
import com.kaviddiss.streamkafka.model.Greetings;
import com.kaviddiss.streamkafka.stream.HelloStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;

@TestConfiguration
@Slf4j
public class TestConfig {
    @Transformer(inputChannel = HelloStreams.OUTPUT, outputChannel = HelloStreams.INPUT)
    public Goodbyes transform(Greetings greetings) {
        Goodbyes goodbyes = Goodbyes.builder()
                .name(greetings.getName())
                .message(greetings.getMessage())
                .build();

        log.info("Sending goodbyes {}", goodbyes);
        return goodbyes;
    }
}
