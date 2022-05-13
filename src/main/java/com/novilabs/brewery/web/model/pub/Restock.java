package com.novilabs.brewery.web.model.pub;

import lombok.Data;

@Data
public class Restock {
    private String pubId;
    private String beerId;
    private Long count;
}
