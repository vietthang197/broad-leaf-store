package com.thanglv.broadleafstore.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thanglv.broadleafstore.entity.AmountAndCurrency;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateProductVariantRequest {

    private String name;
    private Integer quantity;
    private Map<String, String> attributes;
    private Set<CreateVariantOptionValueRequest> optionValues;
    private String sku;
    private AmountAndCurrency price;
    private AmountAndCurrency salePrice;
}
