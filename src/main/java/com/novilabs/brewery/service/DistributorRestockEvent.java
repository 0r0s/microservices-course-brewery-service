package com.novilabs.brewery.service;

import org.springframework.context.ApplicationEvent;

public class DistributorRestockEvent {
    private String distributorId;
    private String upc;
    private Long count;

    public DistributorRestockEvent() {
    }

    public DistributorRestockEvent(String distributorId, String beerId, Long count) {
        this.distributorId = distributorId;
        this.upc = beerId;
        this.count = count;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public String getUpc() {
        return upc;
    }

    public Long getCount() {
        return count;
    }
}
