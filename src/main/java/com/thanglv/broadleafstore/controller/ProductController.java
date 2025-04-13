package com.thanglv.broadleafstore.controller;

import com.thanglv.broadleafstore.entity.*;
import com.thanglv.broadleafstore.repository.*;
import com.thanglv.broadleafstore.request.CreateProductAssetRequest;
import com.thanglv.broadleafstore.request.CreateProductRequest;
import com.thanglv.broadleafstore.request.CreateProductVariantOptionRequest;
import com.thanglv.broadleafstore.request.CreateProductVariantRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = {"*"}, allowCredentials = "true", allowedHeaders = "*")
public class ProductController {

    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductOptionTypeRepository productOptionTypeRepository;
    private final ProductVariantOptionRepository productVariantOptionRepository;
    private final ProductOptionValueRepository productOptionValueRepository;
    private final AssetRepository assetRepository;
    private final ProductAssetsRepository productAssetsRepository;

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return productRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Product> create(@RequestBody CreateProductRequest request) {

        var categoryOptional = categoryRepository.findById(request.getCategory());
        if (categoryOptional.isEmpty())
            throw new RuntimeException("Category not found");
        Category category = categoryOptional.get();

        var product = Product.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .salePrice(request.getSalePrice())
                .regularPrice(request.getRegularPrice())
                .cost(request.getCost())
                .availableOnline(request.getAvailableOnline())
                .quantity(request.getQuantity())
                .category(category)
                .productType(ProductTypeEnum.valueOf(request.getProductType()))
                .attributes(request.getAttributes())
                .sku(request.getSku())
                .createdAt(LocalDateTime.now())
                .build();

        if (ProductTypeEnum.VARIANT.equals(ProductTypeEnum.valueOf(request.getProductType()))) {

            if (CollectionUtils.isNotEmpty(request.getVariantOptions())) {
                Set<ProductVariantOption> variantOptionSet = new HashSet<>();
                for (CreateProductVariantOptionRequest variantOptionRequest : request.getVariantOptions()) {
                    var variantOption = new ProductVariantOption();
                    variantOption.setName(variantOptionRequest.getName());
                    variantOption.setOptionLabel(variantOptionRequest.getOptionLabel());

                    Set<ProductOptionValue> productOptionValueSet = variantOptionRequest
                            .getAllowValues()
                            .stream()
                            .map(item -> {
                                var productOptionValue = new ProductOptionValue();
                                productOptionValue.setValue(item.getValue());
                                productOptionValue.setLabel(item.getLabel());
                                productOptionValue.setOrder(item.getOrder());
                                return productOptionValue;
                            }).collect(Collectors.toSet());

                    List<ProductOptionValue> savedProductOptionValue = productOptionValueRepository.saveAll(productOptionValueSet);
                    variantOption.setAllowValues(new HashSet<>(savedProductOptionValue));
                    var productOptionTypeOptional = productOptionTypeRepository.findById(variantOptionRequest.getProductOptionTypeId());
                    if (productOptionTypeOptional.isEmpty()) {
                        throw new RuntimeException("Product option type not found");
                    }
                    variantOption.setOptionType(productOptionTypeOptional.get());

                    variantOptionSet.add(variantOption);
                }
                variantOptionSet = new HashSet<>(productVariantOptionRepository.saveAll(variantOptionSet));
                product.setVariantOptions(variantOptionSet);
            }

            Set<ProductVariant> productVariantList = new HashSet<>();
            for (CreateProductVariantRequest variantRequest : request.getVariants()) {
                var productVariant = ProductVariant.builder()
                        .name(variantRequest.getName())
                        .attributes(variantRequest.getAttributes())
                        .optionValues(
                                variantRequest.getOptionValues()
                                        .stream()
                                        .map(item -> {
                                            var variantOptionValue = new VariantOptionValue();
                                            variantOptionValue.setValue(item.getValue());
                                            variantOptionValue.setId(item.getId());
                                            return variantOptionValue;
                                        }).collect(Collectors.toSet())
                        )
                        .sku(variantRequest.getSku())
                        .price(variantRequest.getPrice())
                        .salePrice(variantRequest.getSalePrice())
                        .build();
                productVariantList.add(productVariant);
            }
            var savedProductVariant = new HashSet<>(productVariantRepository.saveAll(productVariantList));
            product.setVariants(savedProductVariant);
        }


        if (CollectionUtils.isNotEmpty(request.getAdditionalAssets())) {
            Set<CreateProductAssetRequest> assetRequests = request.getAdditionalAssets();
            List<ProductAssets> productAssetsList = new ArrayList<>();
            for (CreateProductAssetRequest assetRequest : assetRequests) {
                Optional<Asset> assetOptional = assetRepository.findById(assetRequest.getId());
                if (assetOptional.isEmpty()) {
                    throw new RuntimeException("Asset not found with id " + assetRequest.getId());
                }
                var asset = assetOptional.get();
                ProductAssets productAssets = new ProductAssets();
                productAssets.setAsset(asset);
                productAssets.setType(assetRequest.getType());
                productAssets.setIsDeleted(false);
                productAssets.setIsPrimary(assetRequest.getIsPrimary());
                productAssetsList.add(productAssets);
            }
            product.setAdditionalAssets(productAssetsRepository.saveAll(productAssetsList));
        }

        if (request.getPrimaryAsset() != null) {
            CreateProductAssetRequest primaryAssetRequest = request.getPrimaryAsset();
            Optional<Asset> assetOptional = assetRepository.findById(primaryAssetRequest.getId());
            if (assetOptional.isEmpty()) {
                throw new RuntimeException("Asset not found with id " + primaryAssetRequest.getId());
            }
            var asset = assetOptional.get();
            ProductAssets productPrimaryAsset = new ProductAssets();
            productPrimaryAsset.setAsset(asset);
            productPrimaryAsset.setType(primaryAssetRequest.getType());
            productPrimaryAsset.setIsDeleted(false);
            productPrimaryAsset.setIsPrimary(primaryAssetRequest.getIsPrimary());
            product.setPrimaryAsset(productPrimaryAsset);
            product.setPrimaryAsset(productAssetsRepository.save(productPrimaryAsset));

        }
        Product productSaved = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(productSaved);
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


