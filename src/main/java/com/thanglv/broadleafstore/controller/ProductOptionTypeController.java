package com.thanglv.broadleafstore.controller;

import com.thanglv.broadleafstore.entity.ProductOptionType;
import com.thanglv.broadleafstore.repository.ProductOptionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-option-types")
@RequiredArgsConstructor
public class ProductOptionTypeController {

    private final ProductOptionTypeRepository repository;

    @GetMapping
    public List<ProductOptionType> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOptionType> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductOptionType> create(@RequestBody ProductOptionType productOptionType) {
        ProductOptionType saved = repository.save(productOptionType);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOptionType> update(@PathVariable String id, @RequestBody ProductOptionType productOptionType) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productOptionType.setId(id);
        ProductOptionType updated = repository.save(productOptionType);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
