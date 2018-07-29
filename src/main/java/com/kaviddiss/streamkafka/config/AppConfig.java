package com.kaviddiss.streamkafka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class AppConfig {
    @Autowired
    private Environment env;
    
    @Bean
    public AppSettings applicationSettings() {
        AppSettings appSettings = new AppSettings();
        appSettings.partitionId = Integer.parseInt(env.getProperty("spring.cloud.stream.instanceIndex"));
        return appSettings;
    }

    @Bean
    public MessageChannel dummyOutputChannel() {
        return new DirectChannel();
    }
}
