package com.thanglv.broadleafstore.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thanglv.broadleafstore.entity.Asset;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateProductAssetRequest {
    private String id;
    private Boolean isPrimary;
    private String type;
    private String altText;
    private List<String> tags;
    private CreateAssetRequest asset;
}
