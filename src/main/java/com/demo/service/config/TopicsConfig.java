package com.demo.service.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
public class TopicsConfig {

    @Value("${topics.mainTopic.name}")
    public String mainTopic;
    @Value("${topics.mainTopic.routeId}")
    public String topicRouteId;
}
