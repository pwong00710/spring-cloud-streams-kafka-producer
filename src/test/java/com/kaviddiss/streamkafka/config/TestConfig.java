package com.kaviddiss.streamkafka.config;

import com.kaviddiss.streamkafka.model.Goodbyes;
import com.kaviddiss.streamkafka.model.Greetings;
import com.kaviddiss.streamkafka.stream.HelloStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;

@Configuration
@Slf4j
public class TestConfig {
    @Transformer(inputChannel = HelloStreams.OUTPUT, outputChannel = HelloStreams.INPUT)
    public Goodbyes transform(Greetings greetings) {
        Goodbyes goodbyes = new Goodbyes();
        goodbyes.setId(Goodbyes.nextId());
        goodbyes.setName(greetings.getName());
        goodbyes.setMessage(greetings.getMessage());
        goodbyes.setTimestamp(System.currentTimeMillis());
        goodbyes.setRefTxnId(greetings.getTxnId());

        log.info("Sending goodbyes {}", goodbyes);
        return goodbyes;
    }
}
