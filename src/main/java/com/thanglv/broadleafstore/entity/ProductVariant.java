package com.thanglv.broadleafstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductVariant {
    @MongoId
    private String id;
    private String name;
    private Map<String, String> attributes;
    private Set<VariantOptionValue> optionValues;
    private String sku;
    private BigDecimal price;
    private BigDecimal salePrice;

    @DBRef
    private Product product;

    @Data
    public static class VariantOptionValue {
        private String id;
        private String value;
    }
}
