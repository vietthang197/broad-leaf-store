package com.thanglv.broadleafstore.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateProductVariantOptionRequest {
    private String id;
    private String name;
    private String optionLabel;
    private String productOptionTypeId;
    private Set<CreateProductOptionValueRequest> allowValues;
}
