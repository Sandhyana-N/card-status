package com.demo.service.processor;


import com.demo.service.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import com.demo.service.constants.Constants;

@Slf4j
@Component(value = "cardStatusTransformationProcessor")
public class CardStatusTransformationProcessor  implements Processor {

    public void process (Exchange exchange) throws Exception {
        String method = "process";
        LogUtil.info(log,"process", exchange.getProperty(Constants.TRACE_ID, String.class),
                "Entering: " + method + " method in CardStatusTransformationProcessor");

        String body = exchange.getMessage().getBody(String.class);

        exchange.getIn().setBody(body);

        LogUtil.info(log,"process", exchange.getProperty(Constants.TRACE_ID, String.class),
                "Exiting: " + method + " method in CardStatusTransformationProcessor");


    }
}
