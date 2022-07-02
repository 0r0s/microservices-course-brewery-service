package com.novilabs.brewery.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novilabs.brewery.service.DistributorRestockEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.DeserializationException;

public class DistributorRestockEventDeserializer implements Deserializer<DistributorRestockEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DistributorRestockEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                return null;
            }
            return objectMapper.readValue(data, DistributorRestockEvent.class);
        } catch (Exception e) {
            throw new DeserializationException("Error when deserializing DistributorRestockEvent to byte[]", data, false, e);
        }
    }
}
