package com.thanglv.broadleafstore.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductVariantOption {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private String optionLabel;

    @DocumentReference
    private ProductOptionType optionType;

    @DocumentReference
    private Set<ProductOptionValue> allowValues;
}
