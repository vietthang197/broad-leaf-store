package com.thanglv.broadleafstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetsDto {
    private String id;
    private String name;
    private Long size;
    private String extension;
    private LocalDateTime createdAt;
}
