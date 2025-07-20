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
    @Value("topics.retryTopic1.name")
    public String retryTopic1;
    @Value("${topics.retryTopic1.routeId}")
    public String retryTopic1RouteId;
    @Value("${topics.retryTopic1.delayInterval}")
    public int retryTopic1DelayInterval;
    @Value("${topics.mainTopic.groupId}")
    public String mainTopicGroupId;
    @Value("${topics.retryTopic1.groupId}")
    public String retryTopic1GroupId;
    @Value("${topics.mainTopic.consumerCount}")
    public int mainTopicConsumerCount;
    @Value("${topics.retryTopic1.consumerCount}")
    public int retryTopic1ConsumerCount;

}
