package com.thanglv.broadleafstore.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Map;
import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductVariant {
    @MongoId
    private String id;
    private String name;
    private Integer quantity;
    private Map<String, String> attributes;

    @DBRef
    private Set<VariantOptionValue> optionValues;
    private String sku;
    private AmountAndCurrency price;
    private AmountAndCurrency salePrice;
}
