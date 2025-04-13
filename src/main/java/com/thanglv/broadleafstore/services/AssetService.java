package com.thanglv.broadleafstore.services;

import com.thanglv.broadleafstore.dto.AssetsDto;
import com.thanglv.broadleafstore.dto.BaseResponse;
import com.thanglv.broadleafstore.request.FileUploadRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import java.io.IOException;

public interface AssetService {

    BaseResponse<AssetsDto> uploadAssets(FileUploadRequest fileUploadRequest) throws IOException;

    ResponseEntity<Resource> downloadAsset(String assetId) throws IOException;
}
