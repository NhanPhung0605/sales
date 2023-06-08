package com.nhanph.demo.consumer;

import com.nhanph.demo.constant.TopicConstant;
import com.nhanph.demo.payload.response.SalesProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SalesProductConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SalesProductConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = TopicConstant.TOPIC_SALES_PRODUCT, groupId = TopicConstant.GROUP_SALES_PRODUCT, clientIdPrefix = "json", containerFactory = "kafkaListenerObjectContainerFactory")
    public void processSalesProduct(@Payload List<SalesProductResponse> responseList) {
        try {
            log.info("=> response: {}", responseList);
            messagingTemplate.convertAndSend(TopicConstant.TOPIC_SEND_SALES_DATA, responseList);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage());
        }
    }
}
