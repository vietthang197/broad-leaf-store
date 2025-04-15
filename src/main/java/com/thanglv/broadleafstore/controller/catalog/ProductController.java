package com.thanglv.broadleafstore.controller.catalog;

import com.thanglv.broadleafstore.dto.PaginationDto;
import com.thanglv.broadleafstore.entity.*;
import com.thanglv.broadleafstore.repository.*;
import com.thanglv.broadleafstore.request.*;
import com.thanglv.broadleafstore.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = {"*"}, allowCredentials = "true", allowedHeaders = "*")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PaginationDto<Product>> getAll(ProductSearchRequest request) {
        PaginationDto<Product> products = productService.searchProducts(request);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return productRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Product> create(@RequestBody CreateProductRequest request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody UpdateProductRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


