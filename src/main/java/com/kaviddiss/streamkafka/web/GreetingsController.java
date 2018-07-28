package com.kaviddiss.streamkafka.web;

import com.kaviddiss.streamkafka.config.AppSettings;
import com.kaviddiss.streamkafka.model.Greetings;
import com.kaviddiss.streamkafka.service.GreetingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class GreetingsController {
    private final GreetingsService greetingsService;

    public GreetingsController(GreetingsService greetingsService) {
        this.greetingsService = greetingsService;
    }

    @GetMapping("/greetings")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void greetings(@RequestParam("name") String name, @RequestParam("message") String message) {
//        Greetings greetings = Greetings.builder()
//            .id(Greetings.nextId())
//            .txnId(Greetings.nextTxnId())
//            .name(name)
//            .message(message)
//            .timestamp(System.currentTimeMillis())
//            .build();
        Greetings greetings = new Greetings();
        greetings.setId(Greetings.nextId());
        greetings.setTxnId(Greetings.nextTxnId());
        greetings.setName(name);
        greetings.setMessage(message);
        greetings.setTimestamp(System.currentTimeMillis());
        
        greetingsService.sendGreeting(greetings);
    }
}
