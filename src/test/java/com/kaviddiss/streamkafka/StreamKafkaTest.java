package com.kaviddiss.streamkafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaviddiss.streamkafka.config.TestConfig;
import com.kaviddiss.streamkafka.model.Goodbyes;
import com.kaviddiss.streamkafka.model.Greetings;
import com.kaviddiss.streamkafka.service.GoodbyesListener;
import com.kaviddiss.streamkafka.service.GreetingsService;
import com.kaviddiss.streamkafka.stream.HelloStreams;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StreamKafkaApplication.class, TestConfig.class})
@Slf4j
public class StreamKafkaTest {
    @Autowired
    private GreetingsService greetingsService;

    @Autowired
    private HelloStreams helloStreams;

    @Autowired
    private MessageCollector messageCollector;

    private ObjectMapper objectMapper = new ObjectMapper();
    
    @SpyBean
    GoodbyesListener goodbyesListener;
    
    @Test
    public void testSendGreeting() throws Exception {
        String name = "Peter";
        String message = "hello";
        
        Greetings greetings = new Greetings();
        greetings.setId(Greetings.nextId());
        greetings.setTxnId(Greetings.nextTxnId());
        greetings.setName(name);
        greetings.setMessage(message);
        greetings.setTimestamp(System.currentTimeMillis());

        greetingsService.sendGreeting(greetings);
        
        verify(goodbyesListener).handleGoodbyes(any(Goodbyes.class));
    }
        
}
