package com.thanglv.broadleafstore.dto;

import com.thanglv.broadleafstore.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductDto {
    private String id;
    private String name;
    private String slug;
    private String description;
    private String currency;
    private AmountAndCurrency salePrice; // giá KM
    private AmountAndCurrency regularPrice; // giá gốc
    private AmountAndCurrency cost; // giá nhập, giá sản xuất
    private Boolean availableOnline;
    private Integer quantity;
    private CategoryDto category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductTypeEnum productType;
    private List<ProductAttribute> attributes;
    private Set<ProductVariant> variants;
    private Set<ProductVariantOption> variantOptions;
    private String sku;
    private ProductAssetsDto primaryAsset;
    private List<ProductAssetsDto> additionalAssets;
}
