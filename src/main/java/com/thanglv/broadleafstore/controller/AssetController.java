package com.thanglv.broadleafstore.controller;

import com.thanglv.broadleafstore.dto.AssetsDto;
import com.thanglv.broadleafstore.dto.BaseResponse;
import com.thanglv.broadleafstore.request.FileUploadRequest;
import com.thanglv.broadleafstore.services.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @Operation(
            summary = "Upload a file with description",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = FileUploadRequest.class)
                    )
            )
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<AssetsDto>> uploadAssets(@ModelAttribute @Valid FileUploadRequest fileUploadRequest) throws IOException {
        return ResponseEntity.ok(assetService.uploadAssets(fileUploadRequest));
    }

    @GetMapping("/download/{assetId}")
    public ResponseEntity<Resource> downloadAsset(@PathVariable String assetId) throws IOException {
        return assetService.downloadAsset(assetId);
    }
}
