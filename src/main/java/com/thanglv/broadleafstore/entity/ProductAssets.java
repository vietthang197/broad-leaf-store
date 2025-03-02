package com.thanglv.broadleafstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductAssets {

    @MongoId
    private String id;

    @Indexed
    private String productId;

    @Indexed
    private String assetId;

    private String type;

    private Boolean isPrimary;

    private Boolean isDeleted;
}
