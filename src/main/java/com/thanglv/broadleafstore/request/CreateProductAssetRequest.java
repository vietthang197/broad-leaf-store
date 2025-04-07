package com.thanglv.broadleafstore.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProductAssetRequest {
    private String id;
    private Boolean isPrimary;
    private String type;
    private String title;
    private String altText;
    private List<String> tags;
}
