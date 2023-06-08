package com.nhanph.demo.config;

import com.nhanph.demo.constant.TopicConstant;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TopicConfig {

    private final KafkaProperties kafkaProperties;

    @Value("${application.kafka.topic-partitions}")
    private int partitions;

    @Value("${application.kafka.topic-replicas}")
    private int replicas;

    @Autowired
    public TopicConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicSalesProduct() {
        return TopicBuilder.name(TopicConstant.TOPIC_SALES_PRODUCT).partitions(partitions).replicas(replicas).build();
    }
}
