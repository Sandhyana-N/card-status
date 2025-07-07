package com.demo.service.processor;

import com.demo.service.model.Card;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component(value = "cardStatusTransformationProcessor")
public class CardStatusTransformationProcessor  implements Processor {

    public void process (Exchange exchange) throws Exception {

        String body = exchange.getIn().getBody(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Card card = objectMapper.readValue(body,Card.class);

        exchange.getIn().setBody(card);


    }
}
