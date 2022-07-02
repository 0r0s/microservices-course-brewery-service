package com.novilabs.brewery.config.deserializers;

import com.novilabs.brewery.service.DistributorCannotProvideStockAnymoreEvent;
import com.novilabs.brewery.service.DistributorRestockEvent;

public class DistributorRestockFailedEventDeserializer extends ByteArrayDeserializer<DistributorCannotProvideStockAnymoreEvent> {

    @Override
    protected Class<DistributorCannotProvideStockAnymoreEvent> getClassOfObjectBeingDeserialized() {
        return DistributorCannotProvideStockAnymoreEvent.class;
    }
}
