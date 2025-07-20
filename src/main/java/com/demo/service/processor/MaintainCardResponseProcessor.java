package com.demo.service.processor;

import com.demo.service.exception.BusinessException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component(value = "maintainCardResponseProcessor")
public class MaintainCardResponseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String response = exchange.getIn().getBody(String.class);

        Map<String, Object> headers = exchange.getIn().getHeaders();

        String resultCode = headers.get("CamelHttpResponseCode").toString();


        if ("200".equalsIgnoreCase(resultCode)) {
            System.out.println("status of the card updated successfully");
            exchange.getIn().setBody(response);
            exchange.getIn().setHeaders(headers);
        } else {
           throw new BusinessException("",resultCode,true);
        }


    }
}
