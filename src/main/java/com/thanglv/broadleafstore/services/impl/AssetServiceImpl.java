package com.thanglv.broadleafstore.services.impl;

import com.thanglv.broadleafstore.dto.AssetsDto;
import com.thanglv.broadleafstore.dto.BaseResponse;
import com.thanglv.broadleafstore.entity.Asset;
import com.thanglv.broadleafstore.mapper.AssetsMapper;
import com.thanglv.broadleafstore.repository.AssetRepository;
import com.thanglv.broadleafstore.request.FileUploadRequest;
import com.thanglv.broadleafstore.services.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetsMapper assetsMapper;

    @Value("${asset.local.path}")
    private String assetLocalPath;

    @Override
    public BaseResponse<AssetsDto> uploadAssets(FileUploadRequest fileUploadRequest) throws IOException {

        MultipartFile file = fileUploadRequest.getFile();

        LocalDateTime now = LocalDateTime.now();
        Path parentDir = Paths.get(assetLocalPath).resolve(String.valueOf(now.getYear()))
                .resolve(String.valueOf(now.getMonthValue()))
                .resolve(String.valueOf(now.getDayOfMonth()));
        if (Files.notExists(parentDir) && !Files.isDirectory(parentDir)) {
            Files.createDirectories(parentDir);
        }

        String randomBaseName = UUID.randomUUID().toString();
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        String randomFileName = randomBaseName + "." + fileExt;
        String fileLocalPath = parentDir
                .resolve(randomFileName).toAbsolutePath().toString();

        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(fileLocalPath)) {
            IOUtils.copyLarge(inputStream, outputStream);
            Asset asset = new Asset();
            asset.setCreatedAt(now);
            asset.setExtension(fileExt);
            asset.setName(file.getOriginalFilename());
            asset.setSize(file.getSize());
            asset.setLocalPath(fileLocalPath);
            asset.setIsDeleted(false);
            assetRepository.save(asset);
            return BaseResponse.ok(assetsMapper.toDto(asset));
        } catch (IOException e) {
            log.error(e);
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error when upload file", null);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadAsset(String assetId) throws IOException {
        Optional<Asset> assetOptional = assetRepository.findById(assetId);
        if (assetOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Asset asset = assetOptional.get();
        Path filePath = Paths.get(asset.getLocalPath());
        if (Files.notExists(filePath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + asset.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Files.size(filePath))  // Đảm bảo kích thước file được gửi chính xác
                .contentType(MediaType.valueOf(Files.probeContentType(filePath)))
                .body(new InputStreamResource(Files.newInputStream(filePath)));
    }
}
