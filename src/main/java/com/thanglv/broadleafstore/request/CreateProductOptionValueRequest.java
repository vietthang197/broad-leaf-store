package com.thanglv.broadleafstore.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductOptionValueRequest {
    private String id;
    private String value;
    private String label;
    private Integer order;
}
