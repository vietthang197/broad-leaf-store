package com.thanglv.broadleafstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductOptionValue {

    @MongoId
    private String id;
    private String value;
    private String label;
    private Integer order;
}
