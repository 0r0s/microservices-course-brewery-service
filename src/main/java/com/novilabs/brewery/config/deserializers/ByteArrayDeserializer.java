package com.novilabs.brewery.config.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novilabs.brewery.service.DistributorRestockEvent;
import com.novilabs.brewery.service.DistributorRestockFulfilledEvent;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.DeserializationException;

public abstract class ByteArrayDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                return null;
            }
            return objectMapper.readValue(data, getClassOfObjectBeingDeserialized());
        } catch (Exception e) {
            throw new DeserializationException("Error when deserializing DistributorRestockFulfilledEvent to byte[]", data, false, e);
        }
    }

    protected abstract Class<T> getClassOfObjectBeingDeserialized();
}
