package com.novilabs.brewery.web.model.pub;

import lombok.Data;

@Data
public class Order {
    private String id;
    private OrderState orderState;
    private Long remaining;
    private String upc;

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getUpc() {
        return upc;
    }
}
