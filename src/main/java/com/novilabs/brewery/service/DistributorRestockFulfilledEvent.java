package com.novilabs.brewery.service;

public class DistributorRestockFulfilledEvent {
    private String distributorId;
    private String beerId;
    private Long count;

    public DistributorRestockFulfilledEvent() {
    }

    public DistributorRestockFulfilledEvent(String distributorId, String beerId, Long count) {
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
