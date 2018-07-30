package com.kaviddiss.streamkafka.service;

import com.kaviddiss.streamkafka.model.Greetings;
import com.kaviddiss.streamkafka.stream.HelloStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GreetingsService {
    @Autowired
    private HelloStreams helloStreams;

    public void sendGreeting(final Greetings greetings) {
        log.info("Sending greetings {}", greetings);

        MessageChannel messageChannel = helloStreams.outboundGreetings();
        messageChannel.send(MessageBuilder
                .withPayload(greetings)
//                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .setHeader("type", "greetings")
                .build());
    }
}
