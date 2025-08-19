package com.demo.service.processor;

import com.demo.service.constants.Constants;
import com.demo.service.exception.BusinessException;
import com.demo.service.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component(value = "maintainCardResponseProcessor")
public class MaintainCardResponseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String method = "process";
        LogUtil.info(log,method, exchange.getProperty(Constants.TRACE_ID,String.class),
                "Entering: "+method+" method in MaintainCardResponseProcessor");

        String response = exchange.getMessage().getBody(String.class);

        Map<String, Object> headers = exchange.getMessage().getHeaders();

        String resultCode = headers.get("CamelHttpResponseCode").toString();

        if ("200".equalsIgnoreCase(resultCode)) {
            LogUtil.info(log,method, exchange.getProperty(Constants.TRACE_ID,String.class),
                    "Card status updated successfully");
            exchange.getIn().setBody(response);
            exchange.getIn().setHeaders(headers);
        } else if ("404".equalsIgnoreCase(resultCode)) {
            LogUtil.info(log,method, exchange.getProperty(Constants.TRACE_ID,String.class),
                    "Card Not Found");
           throw new BusinessException("Card Not Found",resultCode,true);
        }
        LogUtil.info(log,method, exchange.getProperty(Constants.TRACE_ID,String.class),
                "Exiting: "+method+" method in MaintainCardResponseProcessor");

    }
}
