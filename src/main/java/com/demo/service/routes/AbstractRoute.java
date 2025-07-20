package com.demo.service.routes;

import com.demo.service.config.ApiConfig;
import com.demo.service.config.KafkaConfig;
import com.demo.service.config.TopicsConfig;
import com.demo.service.constants.Constants;
import com.demo.service.exception.BusinessException;
import com.demo.service.util.LogUtil;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.print.DocFlavor;
import javax.xml.ws.spi.http.HttpContext;

public abstract class AbstractRoute extends RouteBuilder {

    @Autowired
    TopicsConfig topicsConfig;

    @Autowired
    KafkaConfig kafkaConfig;

    @Autowired
    ApiConfig apiConfig;

    @Autowired
    @Qualifier(value = "maintainCardProcessor")
    private Processor maintainCardProcessor;

    @Autowired
    @Qualifier(value = "maintainCardResponseProcessor")
    private Processor maintainCardResponseProcessor;

    @Autowired
    @Qualifier(value = "businessExceptionProcessor")
    private Processor businessExceptionProcessor;

    @Autowired
    @Qualifier(value = "cardStatusTransformationProcessor")
    private Processor cardStatusTransformationProcessor;

    @Autowired
    @Qualifier(value = "requestValidationProcessor")
    private Processor requestValidationProcessor;

    private boolean furtherRetryable = true;

    @Override
    public void configure() {

        Endpoint apiEdnpoint = null;
        CamelContext camelContext = getCamelContext();
        HttpComponent httpComponent = camelContext.getComponent("http", HttpComponent.class);

        try {
            apiEdnpoint = httpComponent.createEndpoint(getApiConfig().getUrl());
        } catch (Exception e) {
            LogUtil.error(log, "configure", "Failed to create Endpoint", "Failed to create Endpoint");
        }

        onException(BusinessException.class).handled(true)
                .setHeader(Constants.NEXT_DELAY_TIME, simple(String.valueOf(getDelayInterval()), String.class))
                .process(getBusinessExceptionProcessor())
                .setHeader(KafkaConstants.KEY, exchangeProperty(KafkaConstants.KEY)).to(getRetryPublishTopic())
                .end();

        from(getFromRoute()).setExchangePattern(ExchangePattern.InOut).routeId(getFromTopicRouteId())
                .setHeader(Constants.FURTHER_RETRYABLE, simple(String.valueOf(isFurtherRetryable()), Boolean.class))
                .process(getRequestValidationProcessor())
                .process(getCardStatusTransformationProcessor())
                .process(getMaintainCardProcessor()).to(apiEdnpoint)
                .process(getMaintainCardResponseProcessor())
                .end();
    }


    public ApiConfig getApiConfig() {
        return apiConfig;
    }

    public TopicsConfig getTopicsConfig() {
        return topicsConfig;
    }

    public KafkaConfig getKafkaConfig() {
        return kafkaConfig;
    }

    public String getFromRoute() {
        StringBuilder fromRouteTopic = new StringBuilder("kafka:").append(getFromTopicName()).append("?")
                .append(getKafkaConfig().getConsumerConfig()).append("&groupId=").append(getFromTopicRouteId());
        return fromRouteTopic.toString();
    }

    public String getRetryPublishTopic() {
        StringBuilder fromRouteTopic = new StringBuilder("kafka:").append(getRetryTopicName()).append("?")
                .append(getKafkaConfig().getProducerConfig()).append("&groupId=").append(getFromTopicRouteId());
        return fromRouteTopic.toString();
    }

    protected abstract String getFromTopicName();

    protected abstract String getFromTopicRouteId();

    protected abstract String getFromRouteGroupId();

    protected abstract long getDelayInterval();

    protected abstract String getRetryTopicName();

    protected abstract int getConsumerCount();

    public void setFurtherRetryable(boolean furtherRetryable) {
        this.furtherRetryable = furtherRetryable;
    }

    protected boolean isFurtherRetryable() {
        return this.furtherRetryable;
    }

    public Processor getBusinessExceptionProcessor() {
        return businessExceptionProcessor;
    }

    public Processor getCardStatusTransformationProcessor() {
        return cardStatusTransformationProcessor;
    }

    public Processor getRequestValidationProcessor(){
        return requestValidationProcessor;
    }

    public Processor getMaintainCardProcessor() {
        return maintainCardProcessor;
    }

    public Processor getMaintainCardResponseProcessor() {
        return maintainCardResponseProcessor;
    }
}
