package com.demo.service.processor;

import com.demo.service.constants.Constants;
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
    public void process(Exchange exchange) throws Exception {
        String method = "process";

        final BusinessException businessException = (BusinessException) exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        String errorCode = businessException.getErrorCode();
        boolean isFurtherRetryable = exchange.getProperty(Constants.FURTHER_RETRYABLE, boolean.class);
        String nextRetryTime = null;

        if ("400".equalsIgnoreCase(errorCode)) {
            exchange.setProperty(Constants.FURTHER_RETRYABLE, false);
        }

        if (!isFurtherRetryable) {
            LogUtil.error(log, method, businessException.getErrorCode(), businessException.getErrorMessage());
        } else {
            Long delayTime = exchange.getIn().getHeader(Constants.NEXT_DELAY_TIME, Long.class);
            nextRetryTime = String.valueOf(System.currentTimeMillis() + delayTime);
            exchange.setProperty(Constants.NEXT_DELAY_TIME, nextRetryTime);
        }
    }
}
