package com.novilabs.brewery.service;

import org.springframework.context.ApplicationEvent;

public class DistributorRestockEvent extends ApplicationEvent {
    private String distributorId;
    private String beerId;
    private Long count;
    public DistributorRestockEvent(Object source, String distributorId, String beerId, Long count) {
        super(source);
        this.distributorId = distributorId;
        this.beerId = beerId;
        this.count = count;
    }
}
