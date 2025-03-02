package com.thanglv.broadleafstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
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
public class Product {

    @MongoId
    private String id;

    private String name;
    @Indexed(unique = true)
    private String slug;
    private String description;
    private BigDecimal salePrice; // giá KM
    private BigDecimal regularPrice; // giá gốc
    private BigDecimal cost; // giá nhập, giá sản xuất
    private String status;
    private Integer quantity;
    @Indexed
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductTypeEnum productType;
    private Map<String, String> attributes;
    private Set<ProductVariant> variants;
    private String sku;

}
