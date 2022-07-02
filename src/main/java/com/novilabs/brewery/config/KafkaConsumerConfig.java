package com.novilabs.brewery.config;

import com.novilabs.brewery.config.deserializers.DistributorRestockEventDeserializer;
import com.novilabs.brewery.config.deserializers.DistributorRestockFailedEventDeserializer;
import com.novilabs.brewery.config.deserializers.DistributorRestockFulfilledEventDeserializer;
import com.novilabs.brewery.service.DistributorCannotProvideStockAnymoreEvent;
import com.novilabs.brewery.service.DistributorRestockEvent;
import com.novilabs.brewery.service.DistributorRestockFulfilledEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, DistributorRestockEvent> restockEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                DistributorRestockEventDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DistributorRestockEvent>
    restockKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, DistributorRestockEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(restockEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, DistributorRestockFulfilledEvent> restockFulfilledEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                DistributorRestockFulfilledEventDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DistributorRestockFulfilledEvent>
    restockFulfilledKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, DistributorRestockFulfilledEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(restockFulfilledEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, DistributorCannotProvideStockAnymoreEvent> restockFailedEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                DistributorRestockFailedEventDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DistributorCannotProvideStockAnymoreEvent>
    restockFailedKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, DistributorCannotProvideStockAnymoreEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(restockFailedEventConsumerFactory());
        return factory;
    }
}
