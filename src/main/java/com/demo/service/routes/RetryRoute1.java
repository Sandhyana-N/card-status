package com.demo.service.routes;

import org.springframework.stereotype.Component;

@Component(value = "retryRoute1")
public class RetryRoute1 extends AbstractRoute {

    @Override
    public String getFromTopicName() {
        setFurtherRetryable(false);
        return getTopicsConfig().getRetryTopic1();
    }

    @Override
    public String getRetryTopicName() {
        return null;
    }

    @Override
    public String getFromTopicRouteId() {
        return getTopicsConfig().getRetryTopic1RouteId();
    }

    @Override
    public String getFromRouteGroupId() {
        return getTopicsConfig().getRetryTopic1RouteId();
    }

    @Override
    public long getDelayInterval() {
        return 0;
    }

    @Override
    public int getConsumerCount() {
        return getTopicsConfig().getRetryTopic1ConsumerCount();
    }

    @Override
    public boolean isFurtherRetryable() {
        return false;
    }
}
