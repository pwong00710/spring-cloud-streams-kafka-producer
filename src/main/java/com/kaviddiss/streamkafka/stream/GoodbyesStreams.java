package com.kaviddiss.streamkafka.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface GoodbyesStreams {
    String INPUT = "goodbyes-in";

    @Input(INPUT)
    SubscribableChannel inboundGoodbyes();
}
