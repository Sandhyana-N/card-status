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

    @Value("${kafka.consumerConfig.key-serializer}")
    private String keySerializer;
    @Value("${kafka.consumerConfig.value-serializer}")
    private String valueSerializer;

    private String consumerString;

    private String producerString;

    @PostConstruct
    public void init() {
        consumerString = "brokers=" + brokerList +
                "&groupId=" + groupId +
                "&keyDeserializer=" + keyDeserializer +
                "&valueDeserializer=" + valueDeserializer +
                "&autoOffsetReset=" + autoOffsetReset +
                "&autoCommitEnable=" + enableAutoCommit;

        producerString = "brokers=" + brokerList +
                "&keySerializer=" + keySerializer +
                "&valueSerializer=" + valueSerializer;

    }

    public String getConsumerConfig() {
        return consumerString.toString();
    }

    public String getProducerConfig() {
        return producerString.toString();
    }

}
