package com.demo.service.processor;

import com.demo.service.config.ApiConfig;
import com.demo.service.constants.Constants;
import com.demo.service.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.camel.Processor;

import java.util.Map;


@Slf4j
@Component(value = "maintainCardProcessor")
public class MaintainCardProcessor implements Processor {

    @Autowired
    ApiConfig apiConfig;

    @Override
    public void process(Exchange exchange) throws Exception {
        String method = "process";
        LogUtil.info(log,method, exchange.getProperty(Constants.TRACE_ID,String.class),
                "Entering: "+method+" method in MaintainCardProcessor");
        final String body = exchange.getMessage().getBody(String.class);
        Map<String, Object> headers = setHeaders(exchange);
        exchange.getMessage().setHeaders(headers);
        exchange.getMessage().setBody(body);

        LogUtil.info(log,method, exchange.getProperty(Constants.TRACE_ID,String.class),
                "Exiting: "+method+" method in MaintainCardProcessor");
    }

    public Map<String, Object> setHeaders(Exchange exchange) {

        Map<String, Object> headers = exchange.getIn().getHeaders();
        headers.put(Exchange.HTTP_METHOD, "PUT");
        headers.put(Exchange.HTTP_URI, apiConfig.getUrl());
        headers.put("traceId", "1234");
        headers.put("Content-Type", "application/json");

        return headers;
    }


}
