package com.novilabs.brewery.service;

import org.springframework.context.ApplicationEvent;

public class DistributorRestockEvent extends ApplicationEvent {
    private String distributorId;
    private String upc;
    private Long count;
    public DistributorRestockEvent(Object source, String distributorId, String beerId, Long count) {
        super(source);
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
