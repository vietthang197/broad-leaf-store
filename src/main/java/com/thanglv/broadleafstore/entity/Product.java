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
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @MongoId
    private String id;

    private String name;
    @Indexed(unique = true)
    private String slug;
    private String description;
    private String currency;
    private BigDecimal salePrice; // giá KM
    private BigDecimal regularPrice; // giá gốc
    private BigDecimal cost; // giá nhập, giá sản xuất
    private Boolean availableOnline;
    private Integer quantity;
    @Indexed
    @DBRef
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductTypeEnum productType;
    private Map<String, String> attributes;

    @DBRef
    private Set<ProductVariant> variants;
    private Set<VariantOption> variantOptions;

    private String sku;

    @Data
    public static class VariantOption {

        @Indexed(unique = true)
        private String name;
        private String optionLabel;

        @DBRef
        private ProductOptionType optionType;
        private Set<ProductOptionValue> allowValues;
    }
}
