package com.thanglv.broadleafstore.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetsDto {
    private String id;
    private String name;
    private Long size;
    private String extension;
    private LocalDateTime createdAt;
}
