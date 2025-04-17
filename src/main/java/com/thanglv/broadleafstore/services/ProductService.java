package com.thanglv.broadleafstore.services;

import com.thanglv.broadleafstore.dto.PaginationDto;
import com.thanglv.broadleafstore.dto.ProductDto;
import com.thanglv.broadleafstore.entity.Product;
import com.thanglv.broadleafstore.request.CreateProductRequest;
import com.thanglv.broadleafstore.request.ProductSearchRequest;
import com.thanglv.broadleafstore.request.UpdateProductRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<Product> create(CreateProductRequest request);

    ResponseEntity<Product> update(String id, UpdateProductRequest request);

    ResponseEntity<List<ProductDto>> getPopularProduct();

    PaginationDto<Product> searchProducts(ProductSearchRequest request);
}
