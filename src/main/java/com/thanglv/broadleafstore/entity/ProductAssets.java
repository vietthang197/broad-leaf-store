package com.thanglv.broadleafstore.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductAssets {

    @Id
    private String id;

    private String productId;

    @DocumentReference
    @Indexed
    private Asset asset;

    private String type;

    private Boolean isPrimary;

    private Boolean isDeleted;
}
