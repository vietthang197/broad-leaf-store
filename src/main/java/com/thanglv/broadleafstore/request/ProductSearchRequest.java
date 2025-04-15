package com.thanglv.broadleafstore.request;

import lombok.Data;

@Data
public class ProductSearchRequest {
    private String name;
    private String sku;
    private String category;
    private Boolean availableOnline;
    private Integer page = 0;
    private Integer size = 10;
} 