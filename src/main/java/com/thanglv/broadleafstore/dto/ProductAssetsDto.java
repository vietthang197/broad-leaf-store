package com.thanglv.broadleafstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductAssetsDto {
    private String id;
    private AssetDto asset;
    private String type;
    private Boolean isPrimary;
    private String altText;
    private List<String> tags;
}
