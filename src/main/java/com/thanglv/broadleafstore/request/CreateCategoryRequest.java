package com.thanglv.broadleafstore.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {
    private String name;
    private String slug;
    private String description;
    private String parentCategoryId;
}
