package com.thanglv.broadleafstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

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

    @TextIndexed
    private String name;

    @Indexed(unique = true)
    private String slug;
    private String description;
    private String currency;
    private AmountAndCurrency salePrice; // giá KM
    private AmountAndCurrency regularPrice; // giá gốc
    private AmountAndCurrency cost; // giá nhập, giá sản xuất
    private Boolean availableOnline;
    private Integer quantity;

    @Indexed
    @DBRef
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductTypeEnum productType;
    private Map<String, String> attributes;

    @Indexed
    @DBRef
    private Set<ProductVariant> variants;

    @DBRef
    private Set<ProductVariantOption> variantOptions;

    private String sku;

    @Indexed
    @DBRef
    private Set<ProductAssets> productAssets;
}
