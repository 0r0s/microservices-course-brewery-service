package com.novilabs.brewery.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novilabs.brewery.service.DistributorRestockEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class DistributorRestockEventSerializer implements Serializer<DistributorRestockEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, DistributorRestockEvent data) {
        try {
            if (data == null){
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing DistributorRestockEvent to byte[]");
        }
    }
}
