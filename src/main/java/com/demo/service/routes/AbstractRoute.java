package com.demo.service.routes;

import com.demo.service.config.ApiConfig;
import com.demo.service.config.KafkaConfig;
import com.demo.service.config.TopicsConfig;
import com.demo.service.util.LogUtil;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.builder.RouteBuilder;
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
    private Processor maintainCardPRocessor;

    @Autowired
    @Qualifier(value = "maintainCardResponseProcessor")
    private Processor maintainCardResponseProcessor;

    @Autowired
    @Qualifier(value = "businessExceptionProcessor")
    private Processor businessExceptionProcessor;

    @Autowired
    @Qualifier(value = "cardStatusTransformationProcessor")
    private Processor cardStatusTransformationProcessor;


    @Override
    public void configure(){

        Endpoint apiEdnpoint = null;
        CamelContext camelContext = getCamelContext();
        HttpComponent httpComponent = camelContext.getComponent("HTTP", HttpComponent.class);

        try{
            apiEdnpoint = httpComponent.createEndpoint(getApiConfig().getUrl());
        }catch (Exception e){
            LogUtil.error(log,"configure", "Failed to create Endpoint","Failed to create Endpoint");
        }


        from(getFromRoute()).setExchangePattern(ExchangePattern.InOut).routeId(getFromTopicRouteId())
                .process(getCardStatusTransformationProcessor())
                .process(getMaintainCardPRocessor()).to(apiEdnpoint)
                .process(getMaintainCardResponseProcessor())
                .end();

    }


    public ApiConfig getApiConfig(){
        return apiConfig;
    }

    public TopicsConfig getTopicsConfig(){
        return topicsConfig;
    }

    public String getFromRoute(){
        StringBuilder fromRouteTopic = new StringBuilder("kafka:").append(getFromTopicName());

        return fromRouteTopic.toString();
    }

    protected abstract String getFromTopicName();

    protected abstract String getFromTopicRouteId();

    public Processor getBusinessExceptionProcessor() {
        return businessExceptionProcessor;
    }

    public Processor getCardStatusTransformationProcessor() {
        return cardStatusTransformationProcessor;
    }

    public Processor getMaintainCardPRocessor() {
        return maintainCardPRocessor;
    }

    public Processor getMaintainCardResponseProcessor() {
        return maintainCardResponseProcessor;
    }
}
