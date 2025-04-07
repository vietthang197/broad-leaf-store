package com.thanglv.broadleafstore.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateProductOptionValueRequest {
    private String id;
    private String value;
    private String label;
    private Integer order;
}
