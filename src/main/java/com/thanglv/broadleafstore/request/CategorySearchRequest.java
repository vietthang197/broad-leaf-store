package com.thanglv.broadleafstore.request;

import lombok.Data;

@Data
public class CategorySearchRequest {
    private String name;
    private String slug;
    private String parentCategoryId;
    private Integer page = 0;
    private Integer size = 10;
} 