package com.thanglv.broadleafstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductOption {
    @MongoId
    private String id;

    private String name;
    private String optionLabel;
    private ProductOptionType optionType;
    private Set<ProductOptionValue> allowValues;
}
