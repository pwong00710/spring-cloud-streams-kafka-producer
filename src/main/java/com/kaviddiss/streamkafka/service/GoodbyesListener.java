package com.kaviddiss.streamkafka.service;

import com.kaviddiss.streamkafka.model.Goodbyes;
import com.kaviddiss.streamkafka.stream.HelloStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.reactive.FluxSender;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class GoodbyesListener {
    @Autowired
    MessageChannel dummyOutputChannel;
            
//    @StreamListener(target = HelloStreams.INPUT, condition = "headers['type']=='goodbyes'")
//    public void handleGoodbyes(@Payload Goodbyes goodbyes) {
//        log.info("Received goodbyes: {}", goodbyes);
//
//        dummyOutputChannel.send(MessageBuilder.withPayload(goodbyes).build());
//
//    }
    
    @StreamListener
    public void handleGoodbyes(@Input(HelloStreams.INPUT) Flux<Goodbyes> input,
                               @Output("dummyOutputChannel") FluxSender output) {
        output.send(input.map(goodbyes -> { 
            log.info("Received goodbyes: {}", goodbyes);
            return goodbyes;
        }));
    }
}
