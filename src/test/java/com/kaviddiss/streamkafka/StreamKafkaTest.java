package com.kaviddiss.streamkafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaviddiss.streamkafka.config.TestConfig;
import com.kaviddiss.streamkafka.model.Goodbyes;
import com.kaviddiss.streamkafka.model.Greetings;
import com.kaviddiss.streamkafka.service.DummyService;
import com.kaviddiss.streamkafka.service.GoodbyesListener;
import com.kaviddiss.streamkafka.service.GreetingsService;
import com.kaviddiss.streamkafka.stream.HelloStreams;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StreamKafkaApplication.class})
@AutoConfigureMockMvc
@Slf4j
public class StreamKafkaTest {
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private GreetingsService greetingsService;

    @Autowired
    private HelloStreams helloStreams;

    @Autowired
    private MessageCollector messageCollector;

    private ObjectMapper objectMapper = new ObjectMapper();
    
    @SpyBean
    GoodbyesListener goodbyesListener;

    @SpyBean
    DummyService dummyService;
    
    @Test
    public void testSendGreeting() throws Exception {
        // given
        Greetings greetings = Greetings.builder()
                .name("Peter")
                .message("hello")
                .build();

        // when
        greetingsService.sendGreeting(greetings);
        processGreetings();
        
        // then
//        verify(goodbyesListener).handleGoodbyes(any(), any());
        verify(dummyService).drop(any(Message.class));
        
    }
    
    @Test
    public void testWebSendGreeting() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/greetings")
                    .param("name", "Peter")
                    .param("message", "hello")
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();
        processGreetings();

        // then
        Assert.assertThat(response.getStatus(), equalTo(HttpStatus.ACCEPTED.value()));
//        verify(goodbyesListener).handleGoodbyes(any(), any());
        verify(dummyService).drop(any(Message.class));
    }
    
    private void processGreetings() throws Exception {
        Message<String> sent = (Message<String>) messageCollector.forChannel(helloStreams.outboundGreetings()).poll();

        Greetings greetings = objectMapper.readValue(sent.getPayload(), Greetings.class);
        Goodbyes goodbyes = this.transform(greetings);
        
        helloStreams.inboundGoodbyes().send(MessageBuilder
                .withPayload(goodbyes)
                .setHeader("type", "goodbyes")
                .build());
    }

    private Goodbyes transform(Greetings greetings) {
        Goodbyes goodbyes = Goodbyes.builder()
                .name(greetings.getName())
                .message(greetings.getMessage())
                .build();

        log.info("Sending goodbyes {}", goodbyes);
        return goodbyes;
    }
}
