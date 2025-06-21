package com.demo.service.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class KafkaConfig {

    @Value("${kafka.brokerList}")
    private String brokerList;

    @Value("${kafka.consumerConfig.group-id}")
    private String groupId;

    @Value("${kafka.consumerConfig.key-deserializer}")
    private String keyDeserializer;

    @Value("${kafka.consumerConfig.value-deserializer}")
    private String valueDeserializer;

    @Value("${kafka.consumerConfig.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${kafka.consumerConfig.enable-auto-commit}")
    private boolean enableAutoCommit;

    private StringBuilder consumerString;

    @PostConstruct
    public void  init() {
        consumerString = new StringBuilder("Broker List        : ").append(brokerList).
        append("Group ID           : ").append(groupId).append("\n").
        append("Key Deserializer   : ").append(keyDeserializer).append("\n").
        append("Value Deserializer : ").append(valueDeserializer).append("\n").
        append("Auto Offset Reset  : ").append(autoOffsetReset).append("\n").
        append("Enable Auto Commit : ").append(enableAutoCommit).append("\n");

    }

    public String getConsumerConfig() {
        return consumerString.toString();
    }

}
