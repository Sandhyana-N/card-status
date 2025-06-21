package com.demo.service.processor;

import com.demo.service.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import com.demo.service.util.LogUtil;

@Slf4j
@Component(value = "businessExceptionProcessor")
public class BusinessExceptionProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception{

        final BusinessException businessException = (BusinessException) exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        String errorCode = businessException.getErrorCode();

        if("404".equalsIgnoreCase(errorCode)) {
            LogUtil.error(log, "process", errorCode, "Card Not Found");
        }

    }
}
