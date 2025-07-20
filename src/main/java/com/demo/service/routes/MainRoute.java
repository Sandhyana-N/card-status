package com.demo.service.routes;

import org.springframework.stereotype.Component;

@Component(value = "mainRoute")
public class MainRoute extends AbstractRoute {

    @Override
    public String getFromTopicName() {
        return getTopicsConfig().getMainTopic();
    }

    @Override
    public String getRetryTopicName() {
        return getTopicsConfig().getRetryTopic1();
    }

    public String getFromTopicRouteId() {
        return getTopicsConfig().getTopicRouteId();
    }

    @Override
    public long getDelayInterval() {
        return getTopicsConfig().getRetryTopic1DelayInterval();
    }

    @Override
    public String getFromRouteGroupId() {
        return getTopicsConfig().getMainTopicGroupId();
    }

    @Override
    public int getConsumerCount() {
        return getTopicsConfig().getMainTopicConsumerCount();
    }

}
