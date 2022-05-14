package com.novilabs.brewery.service;

import org.springframework.context.ApplicationEvent;

public class DistributorTakeStockEvent extends ApplicationEvent {
    private String upc;
    private Long count;

    public DistributorTakeStockEvent(Object source, String beerId, Long count) {
        super(source);
        this.upc = beerId;
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
}
