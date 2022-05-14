package com.novilabs.brewery.service;

import org.springframework.context.ApplicationEvent;

public class DistributorRestockEventFulfilled extends ApplicationEvent {
    private String distributorId;
    private String beerId;
    private Long count;

    public DistributorRestockEventFulfilled(Object source, String distributorId, String beerId, Long count) {
        super(source);
        this.distributorId = distributorId;
        this.beerId = beerId;
        this.count = count;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public String getBeerId() {
        return beerId;
    }

    public Long getCount() {
        return count;
    }
}
