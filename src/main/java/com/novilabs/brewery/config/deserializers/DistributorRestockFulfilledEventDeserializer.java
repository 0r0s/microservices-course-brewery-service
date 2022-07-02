package com.novilabs.brewery.config.deserializers;

import com.novilabs.brewery.service.DistributorRestockFulfilledEvent;

public class DistributorRestockFulfilledEventDeserializer extends ByteArrayDeserializer<DistributorRestockFulfilledEvent> {

    @Override
    protected Class<DistributorRestockFulfilledEvent> getClassOfObjectBeingDeserialized() {
        return DistributorRestockFulfilledEvent.class;
    }
}
