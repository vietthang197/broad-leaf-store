package com.thanglv.broadleafstore.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadRequest implements Serializable {

    @Schema(description = "File upload", name = "file", type = "string", format = "binary")
    @NotNull
    private MultipartFile file;
}
