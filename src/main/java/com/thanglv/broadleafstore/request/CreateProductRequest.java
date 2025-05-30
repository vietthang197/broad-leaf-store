package com.thanglv.broadleafstore.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thanglv.broadleafstore.dto.ProductAttribute;
import com.thanglv.broadleafstore.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateProductRequest {
    private String sku;
    private String name;
    private String slug;
    private String description;
    private String currency;
    private AmountAndCurrency salePrice; // giá KM
    private AmountAndCurrency regularPrice; // giá gốc
    private AmountAndCurrency cost; // giá nhập, giá sản xuất
    private Boolean availableOnline;
    private Integer quantity;
    private String category;
    private String productType;
    private List<ProductAttribute> attributes;
    private List<CreateProductVariantRequest> variants;
    private List<CreateProductVariantOptionRequest> variantOptions;
    private List<CreateProductAssetRequest> additionalAssets;
    private CreateProductAssetRequest primaryAsset;
}
