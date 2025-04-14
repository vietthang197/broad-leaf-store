package com.thanglv.broadleafstore.services.impl;

import com.thanglv.broadleafstore.entity.*;
import com.thanglv.broadleafstore.repository.*;
import com.thanglv.broadleafstore.request.*;
import com.thanglv.broadleafstore.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductOptionTypeRepository productOptionTypeRepository;
    private final ProductVariantOptionRepository productVariantOptionRepository;
    private final ProductOptionValueRepository productOptionValueRepository;
    private final AssetRepository assetRepository;
    private final ProductAssetsRepository productAssetsRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Product> create(CreateProductRequest request) {

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
                Optional<Asset> assetOptional = assetRepository.findById(assetRequest.getAsset().getId());
                if (assetOptional.isEmpty()) {
                    throw new RuntimeException("Asset not found with id " + assetRequest.getAsset().getId());
                }
                var asset = assetOptional.get();
                ProductAssets productAssets = new ProductAssets();
                productAssets.setAsset(asset);
                productAssets.setType(assetRequest.getType());
                productAssets.setIsDeleted(false);
                productAssets.setIsPrimary(assetRequest.getIsPrimary());
                productAssets.setAltText(assetRequest.getAltText());
                productAssets.setTags(assetRequest.getTags());
                productAssets.setCreatedAt(LocalDateTime.now());
                productAssetsList.add(productAssets);
            }
            product.setAdditionalAssets(productAssetsRepository.saveAll(productAssetsList));
        }

        if (request.getPrimaryAsset() != null) {
            CreateProductAssetRequest primaryAssetRequest = request.getPrimaryAsset();
            Optional<Asset> assetOptional = assetRepository.findById(primaryAssetRequest.getAsset().getId());
            if (assetOptional.isEmpty()) {
                throw new RuntimeException("Asset not found with id " + primaryAssetRequest.getAsset().getId());
            }
            var asset = assetOptional.get();
            ProductAssets productPrimaryAsset = new ProductAssets();
            productPrimaryAsset.setAsset(asset);
            productPrimaryAsset.setType(primaryAssetRequest.getType());
            productPrimaryAsset.setIsDeleted(false);
            productPrimaryAsset.setIsPrimary(primaryAssetRequest.getIsPrimary());
            productPrimaryAsset.setAltText(primaryAssetRequest.getAltText());
            productPrimaryAsset.setTags(primaryAssetRequest.getTags());
            productPrimaryAsset.setCreatedAt(LocalDateTime.now());
            product.setPrimaryAsset(productAssetsRepository.save(productPrimaryAsset));

        }
        Product productSaved = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(productSaved);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Product> update(String id, UpdateProductRequest request) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product not found with id " + id);
        }
        Product product = productOptional.get();

        // check category
        var categoryOptional = categoryRepository.findById(request.getCategory());
        if (categoryOptional.isEmpty())
            throw new RuntimeException("Category not found");
        Category category = categoryOptional.get();

        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setSalePrice(request.getSalePrice());
        product.setRegularPrice(request.getRegularPrice());
        product.setCost(request.getCost());
        product.setAvailableOnline(request.getAvailableOnline());
        product.setQuantity(request.getQuantity());
        product.setCategory(category);
        product.setAttributes(request.getAttributes());
        product.setSku(request.getSku());
        product.setCreatedAt(LocalDateTime.now());

        //  update variant
        return null;
    }
}
