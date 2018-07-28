package com.kaviddiss.streamkafka.config;

import com.kaviddiss.streamkafka.model.Goodbyes;
import com.kaviddiss.streamkafka.model.Greetings;
import com.kaviddiss.streamkafka.stream.HelloStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Transformer;

@EnableBinding({HelloStreams.class})
public class StreamsConfig {
}
