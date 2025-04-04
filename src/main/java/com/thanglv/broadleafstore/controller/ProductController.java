package com.thanglv.broadleafstore.controller;

import com.thanglv.broadleafstore.entity.*;
import com.thanglv.broadleafstore.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductOptionTypeRepository productOptionTypeRepository;
    private final ProductVariantOptionRepository productVariantOptionRepository;
    private final ProductOptionValueRepository productOptionValueRepository;

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
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
                .sku(request.getSku())
                .productType(request.getProductType())
                .build();

        if (CollectionUtils.isNotEmpty(request.getVariantOptions())) {
            Set<ProductVariantOption> variantOptionSet = new HashSet<>();
            for (ProductVariantOption variantOptionRequest : request.getVariantOptions()) {
                ProductVariantOption variantOption = new ProductVariantOption();
                variantOption.setName(variantOptionRequest.getName());
                variantOption.setOptionLabel(variantOptionRequest.getOptionLabel());
                variantOption.setAllowValues(new HashSet<>(productOptionValueRepository.saveAll(variantOptionRequest.getAllowValues())));
                Optional<ProductOptionType> productOptionTypeOptional = productOptionTypeRepository.findById(variantOptionRequest.getOptionType().getId());
                if (productOptionTypeOptional.isEmpty()) {
                    throw new RuntimeException("Product option type not found");
                }
                variantOption.setOptionType(productOptionTypeOptional.get());

                variantOptionSet.add(variantOption);
            }
            variantOptionSet = new HashSet<>(productVariantOptionRepository.saveAll(variantOptionSet));
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

        Product saved = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        Product updated = productRepository.save(product);
        return ResponseEntity.ok(updated);
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


