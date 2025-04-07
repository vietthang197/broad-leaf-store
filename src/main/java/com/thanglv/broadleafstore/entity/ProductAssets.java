package com.thanglv.broadleafstore.entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductAssets {

    @MongoId
    private String id;

    @DBRef
    @Indexed
    private Asset asset;

    private String type;

    private Boolean isPrimary;

    private Boolean isDeleted;
}
