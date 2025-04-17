package com.thanglv.broadleafstore.controller.front;

import com.thanglv.broadleafstore.dto.ProductDto;
import com.thanglv.broadleafstore.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store-front")
@CrossOrigin(originPatterns = {"*"}, allowCredentials = "true", allowedHeaders = "*")
@RequiredArgsConstructor
public class StoreFrontController {

    private final ProductService productService;

    @GetMapping("/popular-product")
    public ResponseEntity<List<ProductDto>> getPopularProduct() {
        return productService.getPopularProduct();
    }
}
