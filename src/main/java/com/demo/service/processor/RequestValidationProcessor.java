package com.demo.service.processor;

import com.demo.service.constants.Constants;
import com.demo.service.exception.BusinessException;
import com.demo.service.util.LogUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = "requestValidationProcessor")
public class RequestValidationProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String method = "process";
        LogUtil.info(log,method, exchange.getProperty(Constants.TRACE_ID,String.class),
                "Entering: "+method+" method in RequestValidationProcessor");

        String request = exchange.getIn().getBody().toString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(request);

        String traceId = jsonNode.get("traceId").asText();

        if(StringUtils.isBlank(traceId)){
            LogUtil.error(log,method,"5001","Missing traceId");
            throw new BusinessException("Missing traceId", "5001", false);
        }


        LogUtil.info(log,method, exchange.getProperty(Constants.TRACE_ID,String.class),
                "Exiting: "+method+" method in RequestValidationProcessor");

    }

}
