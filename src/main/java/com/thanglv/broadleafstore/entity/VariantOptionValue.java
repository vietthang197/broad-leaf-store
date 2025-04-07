package com.thanglv.broadleafstore.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VariantOptionValue {

    @MongoId
    private String id;
    private String value;
}
