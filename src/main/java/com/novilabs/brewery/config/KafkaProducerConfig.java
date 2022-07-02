package com.novilabs.brewery.config;

import com.novilabs.brewery.config.serializers.DistributorRestockEventSerializer;
import com.novilabs.brewery.config.serializers.DistributorRestockFulfilledEventSerializer;
import com.novilabs.brewery.service.DistributorRestockEvent;
import com.novilabs.brewery.service.DistributorRestockFulfilledEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, DistributorRestockEvent> restockEventProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                DistributorRestockEventSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, DistributorRestockEvent> restockEventKafkaTemplate() {
        return new KafkaTemplate<>(restockEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, DistributorRestockFulfilledEvent> distributorRestockFulfilledEventProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                DistributorRestockFulfilledEventSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, DistributorRestockFulfilledEvent> distributorRestockFulfilledEventKafkaTemplate() {
        return new KafkaTemplate<>(distributorRestockFulfilledEventProducerFactory());
    }
}