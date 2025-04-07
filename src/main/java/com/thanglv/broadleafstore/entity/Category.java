package com.thanglv.broadleafstore.entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {

    @MongoId
    private String id;

    private String name;
    @Indexed(unique = true)
    private String slug;
    private String description;
    private String parentCategoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
