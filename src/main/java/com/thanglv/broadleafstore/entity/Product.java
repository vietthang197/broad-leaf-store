package com.thanglv.broadleafstore.entity;

import com.thanglv.broadleafstore.dto.ProductAttribute;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
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
    @DocumentReference
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductTypeEnum productType;
    private List<ProductAttribute> attributes;

    @Indexed
    @DocumentReference
    private Set<ProductVariant> variants;

    @DocumentReference
    private Set<ProductVariantOption> variantOptions;

    private String sku;

    @Indexed
    @DocumentReference
    private ProductAssets primaryAsset;

    @Indexed
    @DocumentReference
    private List<ProductAssets> additionalAssets;
}
