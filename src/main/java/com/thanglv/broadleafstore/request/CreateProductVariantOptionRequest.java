package com.thanglv.broadleafstore.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateProductVariantOptionRequest {
    private String name;
    private String optionLabel;
    private String productOptionTypeId;
    private Set<CreateProductOptionValueRequest> allowValues;
}
