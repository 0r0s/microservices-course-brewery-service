package com.novilabs.brewery.config.deserializers;

import com.novilabs.brewery.service.DistributorHasStockEvent;

public class DistributorHasNewStockEventDeserializer extends ByteArrayDeserializer<DistributorHasStockEvent> {

    @Override
    protected Class<DistributorHasStockEvent> getClassOfObjectBeingDeserialized() {
        return DistributorHasStockEvent.class;
    }
}
