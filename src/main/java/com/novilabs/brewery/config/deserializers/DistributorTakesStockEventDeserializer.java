package com.novilabs.brewery.config.deserializers;

import com.novilabs.brewery.service.DistributorTakeStockEvent;

public class DistributorTakesStockEventDeserializer extends ByteArrayDeserializer<DistributorTakeStockEvent> {
    @Override
    protected Class<DistributorTakeStockEvent> getClassOfObjectBeingDeserialized() {
        return DistributorTakeStockEvent.class;
    }
}
