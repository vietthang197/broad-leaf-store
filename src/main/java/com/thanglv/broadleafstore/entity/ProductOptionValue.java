package com.thanglv.broadleafstore.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductOptionValue {

    @MongoId
    private String id;
    private String value;
    private String label;
    private Integer order;
}
