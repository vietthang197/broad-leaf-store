package com.thanglv.broadleafstore.services;

import com.thanglv.broadleafstore.entity.Product;
import com.thanglv.broadleafstore.request.CreateProductRequest;
import com.thanglv.broadleafstore.request.UpdateProductRequest;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<Product> create(CreateProductRequest request);

    ResponseEntity<Product> update(String id, UpdateProductRequest request);
}
