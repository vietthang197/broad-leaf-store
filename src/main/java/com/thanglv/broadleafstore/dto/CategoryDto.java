package com.thanglv.broadleafstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDto {
    private String id;
    private String name;
    private String slug;
    private String description;
    private String parentCategoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
