package com.thanglv.broadleafstore.repository;

import com.thanglv.broadleafstore.entity.ProductVariantOption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantOptionRepository extends MongoRepository<ProductVariantOption, String> {
}
