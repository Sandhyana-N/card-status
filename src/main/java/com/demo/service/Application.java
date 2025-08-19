package com.demo.service;

import com.demo.service.processor.TestMessageSender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(exclude = { KafkaAutoConfiguration.class })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }

    @Bean
    CommandLineRunner run(TestMessageSender localProcessor) {
        return args -> {
            String hardcodedMessage = "{\n" +
                    "    \"cardNumber\" : \"9845691319\",\n" +
                    "    \"cardType\" : \"RUPAY\",\n" +
                    "    \"cardStatus\": \"Inactive\"\n" +
                    "}";
            localProcessor.sendHardcodedMessageWithRetry(hardcodedMessage, 2, 60000);
        };
    }

    public void dummy() {

    }
}
