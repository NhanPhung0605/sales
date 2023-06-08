package com.nhanph.demo.producer;

import com.nhanph.demo.constant.TopicConstant;
import com.nhanph.demo.payload.response.SalesProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SalesProductProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public SalesProductProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSalesProduct(List<SalesProductResponse> responses) {
        log.info("Entering sendSalesProduct()... ");
        Message<List<SalesProductResponse>> message = MessageBuilder.withPayload(responses).setHeader(KafkaHeaders.TOPIC, TopicConstant.TOPIC_SALES_PRODUCT).build();

        kafkaTemplate.send(message);
    }
}
