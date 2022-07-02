package com.novilabs.brewery.service;

import org.springframework.context.ApplicationEvent;

public class DistributorHasStockEvent {
    private String distributorId;
    private String upc;
    private Long count;

    public DistributorHasStockEvent() {
    }

    public DistributorHasStockEvent(String distributorId, String upc, Long count) {
        this.distributorId = distributorId;
        this.upc = upc;
        this.count = count;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getDistributorId() {
        return distributorId;
    }
}
