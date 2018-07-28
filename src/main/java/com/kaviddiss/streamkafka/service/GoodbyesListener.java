package com.kaviddiss.streamkafka.service;

import com.kaviddiss.streamkafka.model.Goodbyes;
import com.kaviddiss.streamkafka.stream.HelloStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoodbyesListener {
    @StreamListener(HelloStreams.INPUT)
    public void handleGoodbyes(@Payload Goodbyes goodbyes) {
        log.info("Received goodbyes: {}", goodbyes);
    }
}
