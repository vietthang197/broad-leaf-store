package com.thanglv.broadleafstore.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Map;
import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductVariant {
    @Id
    private String id;
    private String name;
    private Integer quantity;
    private Map<String, String> attributes;

    @DocumentReference
    private Set<VariantOptionValue> optionValues;
    private String sku;
    private AmountAndCurrency price;
    private AmountAndCurrency salePrice;
}
