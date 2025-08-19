package com.demo.service.processor;

import com.demo.service.constants.Constants;
import com.demo.service.exception.BusinessException;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.springframework.stereotype.Component;

@Component
public class TestMessageSender {

    private final CamelContext camelContext;
    private final ProducerTemplate producerTemplate;
    private final Processor requestValidationProcessor;
    private final Processor cardStatusTransformationProcessor;
    private final Processor maintainCardProcessor;
    private final Processor maintainCardResponseProcessor;
    private final Processor businessExceptionProcessor;

    public TestMessageSender(CamelContext camelContext,
                                 Processor requestValidationProcessor,
                                 Processor cardStatusTransformationProcessor,
                                 Processor maintainCardProcessor,
                                 Processor maintainCardResponseProcessor,
                                 ProducerTemplate producerTemplate,
                                 Processor businessExceptionProcessor) {
        this.camelContext = camelContext;
        this.requestValidationProcessor = requestValidationProcessor;
        this.cardStatusTransformationProcessor = cardStatusTransformationProcessor;
        this.maintainCardProcessor = maintainCardProcessor;
        this.maintainCardResponseProcessor = maintainCardResponseProcessor;
        this.producerTemplate = producerTemplate;
        this.businessExceptionProcessor = businessExceptionProcessor;
    }

    public void sendHardcodedMessageWithRetry(String message, int maxRetries, long retryDelayMillis) throws Exception {
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            attempt++;
            Exchange exchange = new DefaultExchange(camelContext);
            exchange.getIn().setBody(message);

            try {
                // Pre-processors
                requestValidationProcessor.process(exchange);
                cardStatusTransformationProcessor.process(exchange);
                maintainCardProcessor.process(exchange);

                // Send to downstream
                Exchange response = producerTemplate.send(
                        "http://localhost:8444/api/cards/update/status?throwExceptionOnFailure=false", exchange
                );

                // Process response
                maintainCardResponseProcessor.process(response);

                // If no exception thrown, mark success
                success = true;
                System.out.println("Message processed successfully: " + response.getMessage().getBody(String.class));

            } catch (BusinessException ex) {
                // Set in exchange for BusinessExceptionProcessor
                exchange.setProperty(Exchange.EXCEPTION_CAUGHT, ex);
                businessExceptionProcessor.process(exchange);

                // Check if further retry is allowed
                Boolean furtherRetryable = exchange.getProperty(Constants.FURTHER_RETRYABLE, Boolean.class);
                if (furtherRetryable != null && furtherRetryable) {
                    System.out.println("Retrying attempt " + attempt + " after " + retryDelayMillis + " ms");
                    Thread.sleep(retryDelayMillis);
                } else {
                    exchange.setProperty(Constants.FURTHER_RETRYABLE, false);
                    System.out.println("Not retryable, stopping at attempt " + attempt);
                    throw ex; // rethrow if you want to fail the sender
                }
            }
        }
    }

}


