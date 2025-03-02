package com.thanglv.broadleafstore.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadRequest implements Serializable {

    @Schema(description = "File upload", name = "file", type = "string", format = "binary")
    @NotNull
    private MultipartFile file;
}
