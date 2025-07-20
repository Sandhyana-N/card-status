package com.demo.service.processor;

import com.demo.service.config.ApiConfig;
import com.demo.service.constants.Constants;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.camel.Processor;

import java.util.Map;

@Component(value = "maintainCardProcessor")
public class MaintainCardProcessor implements Processor {

    @Autowired
    ApiConfig apiConfig;

    @Override
    public void process(Exchange exchange) throws Exception {

        final String body = exchange.getIn().getBody(String.class);
        Map<String, Object> headers = setHeaders(exchange);
        exchange.getIn().setHeaders(headers);
        exchange.getIn().setBody(body);
    }

    public Map<String, Object> setHeaders(Exchange exchange) {

        Map<String, Object> headers = exchange.getIn().getHeaders();
        headers.put(Exchange.HTTP_METHOD, "PUT");
        headers.put(Exchange.HTTP_URI, apiConfig.getUrl());
        headers.put("traceId", exchange.getProperty(Constants.TRACE_ID));
        headers.put("Content-Type", "application/json");

        return headers;
    }


}
