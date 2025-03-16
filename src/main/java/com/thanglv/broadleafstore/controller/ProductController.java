package com.thanglv.broadleafstore.controller;

import com.thanglv.broadleafstore.entity.*;
import com.thanglv.broadleafstore.repository.CategoryRepository;
import com.thanglv.broadleafstore.repository.ProductOptionTypeRepository;
import com.thanglv.broadleafstore.repository.ProductRepository;
import com.thanglv.broadleafstore.repository.ProductVariantRepository;
import com.thanglv.broadleafstore.util.Constant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductOptionTypeRepository productOptionTypeRepository;

    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product request) {

        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategory().getId());
        if (categoryOptional.isEmpty())
            throw new RuntimeException("Category not found");
        Category category = categoryOptional.get();

        Product product = Product.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .salePrice(request.getSalePrice())
                .regularPrice(request.getRegularPrice())
                .cost(request.getCost())
                .availableOnline(request.getAvailableOnline())
                .quantity(request.getQuantity())
                .category(category)
                .productType(request.getProductType())
                .attributes(request.getAttributes())
                .build();

        if (CollectionUtils.isNotEmpty(request.getVariantOptions())) {
            Set<Product.VariantOption> variantOptionSet = new HashSet<>();
            for (Product.VariantOption variantOptionRequest : request.getVariantOptions()) {
                Product.VariantOption variantOption = new Product.VariantOption();
                variantOption.setName(variantOptionRequest.getName());
                variantOption.setOptionLabel(variantOptionRequest.getOptionLabel());
                variantOption.setAllowValues(variantOptionRequest.getAllowValues());
                Optional<ProductOptionType> productOptionTypeOptional = productOptionTypeRepository.findById(variantOptionRequest.getOptionType().getId());
                if (productOptionTypeOptional.isEmpty()) {
                    throw new RuntimeException("Product option type not found");
                }
                variantOption.setOptionType(productOptionTypeOptional.get());
                variantOptionSet.add(variantOption);
            }
            product.setVariantOptions(variantOptionSet);
        }

        if (ProductTypeEnum.VARIANT.equals(request.getProductType())) {
            Set<ProductVariant> productVariantList = new HashSet<>();
            for (ProductVariant variantRequest : request.getVariants()) {
                ProductVariant productVariant = ProductVariant.builder()
                        .name(variantRequest.getName())
                        .attributes(variantRequest.getAttributes())
                        .optionValues(variantRequest.getOptionValues())
                        .sku(variantRequest.getSku())
                        .price(variantRequest.getPrice())
                        .salePrice(variantRequest.getSalePrice())
                        .build();
                productVariantList.add(productVariant);
            }
            Set<ProductVariant> savedProductVariant = new HashSet<>(productVariantRepository.saveAll(productVariantList));
            product.setVariants(savedProductVariant);
        }

        Product saved = repository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product product) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        Product updated = repository.save(product);
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


