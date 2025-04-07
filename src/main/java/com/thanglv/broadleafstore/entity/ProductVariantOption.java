package com.thanglv.broadleafstore.entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductVariantOption {

    @MongoId
    private String id;

    @Indexed(unique = true)
    private String name;
    private String optionLabel;

    @DBRef
    private ProductOptionType optionType;

    @DBRef
    private Set<ProductOptionValue> allowValues;
}
