package com.novilabs.brewery.config.deserializers;

import com.novilabs.brewery.service.DistributorRestockEvent;

public class DistributorRestockEventDeserializer extends ByteArrayDeserializer<DistributorRestockEvent> {

    @Override
    protected Class<DistributorRestockEvent> getClassOfObjectBeingDeserialized() {
        return DistributorRestockEvent.class;
    }
}
