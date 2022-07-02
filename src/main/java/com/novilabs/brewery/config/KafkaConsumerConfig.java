package com.novilabs.brewery.config;

import com.novilabs.brewery.config.deserializers.*;
import com.novilabs.brewery.service.*;
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
        Map<String, Object> props = getBaseProps();
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
        Map<String, Object> props = getBaseProps();
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
        Map<String, Object> props = getBaseProps();
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

    @Bean
    public ConsumerFactory<String, DistributorTakeStockEvent> distributorTakesStockEventConsumerFactory() {
        Map<String, Object> props = getBaseProps();
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                DistributorTakesStockEventDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DistributorTakeStockEvent>
    distributorTakesStockKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, DistributorTakeStockEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(distributorTakesStockEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, DistributorHasStockEvent> distributorHasNewStockEventConsumerFactory() {
        Map<String, Object> props = getBaseProps();
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                DistributorHasNewStockEventDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DistributorHasStockEvent>
    distributorHasNewStockKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, DistributorHasStockEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(distributorHasNewStockEventConsumerFactory());
        return factory;
    }

    private Map<String, Object> getBaseProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        return props;
    }
}
